#!/usr/bin/env bash
# =============================================================================
# configure_github_repo.sh
# Configura las variables del repositorio en GitHub Actions para el pipeline
# Serenity Smoke Tests.
#
# Requisitos:
#   - GitHub CLI (gh) instalado y autenticado: gh auth login
#   - Ejecutar desde la raíz del repositorio o pasar GITHUB_REPO como variable
#
# Uso:
#   bash scripts/configure_github_repo.sh
#   GITHUB_REPO=owner/repo bash scripts/configure_github_repo.sh
# =============================================================================
set -euo pipefail

# ── Detectar repositorio ──────────────────────────────────────────────────────
REPO="${GITHUB_REPO:-$(gh repo view --json nameWithOwner -q .nameWithOwner 2>/dev/null || true)}"

if [ -z "${REPO}" ]; then
  echo "ERROR: No se pudo detectar el repositorio."
  echo "  Define GITHUB_REPO=owner/repo y vuelve a intentarlo."
  exit 1
fi

echo "Configurando variables en: ${REPO}"
echo ""

# ── Variables del pipeline ────────────────────────────────────────────────────

# URL del repositorio que contiene el sistema bajo prueba (Hotel MVP)
SERENITY_TARGET_REPO_URL="${SERENITY_TARGET_REPO_URL:-https://github.com/EGgames/HOTEL-MVP.git}"

# Rama del repositorio objetivo
SERENITY_TARGET_REPO_BRANCH="${SERENITY_TARGET_REPO_BRANCH:-dev}"

# Habilitar creación automática de issues cuando la suite falle (true | false)
SERENITY_FAILURE_ISSUES_ENABLED="${SERENITY_FAILURE_ISSUES_ENABLED:-false}"

# ── Función helper ────────────────────────────────────────────────────────────
set_var() {
  local name="$1"
  local value="$2"
  echo "  Seteando ${name}=${value}"
  gh variable set "${name}" \
    --repo "${REPO}" \
    --body "${value}"
}

# ── Aplicar variables ─────────────────────────────────────────────────────────
echo "Variables de repositorio de GitHub Actions:"
set_var SERENITY_TARGET_REPO_URL      "${SERENITY_TARGET_REPO_URL}"
set_var SERENITY_TARGET_REPO_BRANCH   "${SERENITY_TARGET_REPO_BRANCH}"
set_var SERENITY_FAILURE_ISSUES_ENABLED "${SERENITY_FAILURE_ISSUES_ENABLED}"

echo ""
echo "Configuración completada."
echo ""
echo "Variables actuales del repositorio:"
gh variable list --repo "${REPO}"
echo ""
echo "Para habilitar GitHub Pages con GitHub Actions como fuente:"
echo "  gh api repos/${REPO}/pages -X POST \\"
echo "    -f source.branch=gh-pages \\"
echo "    -f source.path=/ \\"
echo "    -f build_type=workflow \\"
echo "    || gh api repos/${REPO}/pages -X PUT -f build_type=workflow"
echo ""
echo "O desde la interfaz web:"
echo "  Settings → Pages → Source → GitHub Actions"
