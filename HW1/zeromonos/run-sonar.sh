#!/bin/bash

# Script para executar análise de qualidade de código
# Uso: ./run-sonar.sh [jacoco|local]

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

MODE=${1:-jacoco}

echo "🔍 Executando análise de qualidade de código..."
echo ""

# Gerar relatório de cobertura primeiro
echo "📊 Gerando relatório de cobertura (JaCoCo)..."
echo "   Executando testes..."
mvn clean test jacoco:report || {
    echo ""
    echo "⚠️  AVISO: Alguns testes falharam, mas o relatório JaCoCo será gerado com os testes que passaram."
    echo "   Executando apenas geração de relatório JaCoCo..."
    mvn jacoco:report || true
}

echo ""
echo "📈 Relatório JaCoCo disponível em: target/site/jacoco/index.html"
echo "   Abrir com: open target/site/jacoco/index.html"
echo ""

if [ "$MODE" = "local" ]; then
    echo "🏠 Executando análise SonarQube local..."
    echo "   (Certifique-se de que o SonarQube está a correr em http://localhost:9000)"
    echo ""
    
    # Verificar se o servidor está acessível
    if ! curl -s -f http://localhost:9000/api/system/status > /dev/null 2>&1; then
        echo "⚠️  AVISO: Não foi possível conectar ao SonarQube em http://localhost:9000"
        echo ""
        echo "Para executar análise SonarQube:"
        echo "1. Iniciar o servidor SonarQube"
        echo "2. Aceder a http://localhost:9000 e fazer login (admin/admin por padrão)"
        echo "3. Criar um novo projeto ou usar um projeto existente"
        echo "4. Executar novamente: ./run-sonar.sh local"
        echo ""
        echo "ℹ️  Relatório JaCoCo já foi gerado e está disponível."
        exit 0
    fi
    
    mvn sonar:sonar
    
    echo ""
    echo "✅ Análise SonarQube concluída! Ver resultados em: http://localhost:9000"
    echo ""
    echo "💡 Dica: O relatório JaCoCo pode ser visualizado independentemente do SonarQube:"
    echo "   open target/site/jacoco/index.html"
fi

