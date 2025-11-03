#!/bin/bash

# Script para configurar token do SonarQube no Maven settings.xml
# Uso: ./setup-sonar-token.sh SEU_TOKEN_AQUI

if [ $# -eq 0 ]; then
    echo "❌ Erro: Por favor forneça o token do SonarQube"
    echo "Uso: ./setup-sonar-token.sh SEU_TOKEN_AQUI"
    echo ""
    echo "Para obter o token:"
    echo "1. Aceda a http://localhost:9000"
    echo "2. Login: admin/admin"
    echo "3. My Account → Security → Generate Token"
    exit 1
fi

TOKEN=$1
M2_DIR="$HOME/.m2"
SETTINGS_FILE="$M2_DIR/settings.xml"

echo "🔧 Configurando token do SonarQube..."

# Criar diretório .m2 se não existir
mkdir -p "$M2_DIR"

# Verificar se settings.xml existe
if [ -f "$SETTINGS_FILE" ]; then
    # Verificar se já existe configuração do SonarQube
    if grep -q "sonar.host.url" "$SETTINGS_FILE"; then
        echo "ℹ️  Atualizando configuração existente do SonarQube..."
        # Usar sed para atualizar o token
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # macOS
            sed -i '' "s|<sonar.login>.*</sonar.login>|<sonar.login>$TOKEN</sonar.login>|g" "$SETTINGS_FILE"
        else
            # Linux
            sed -i "s|<sonar.login>.*</sonar.login>|<sonar.login>$TOKEN</sonar.login>|g" "$SETTINGS_FILE"
        fi
        echo "✅ Token atualizado com sucesso!"
    else
        echo "ℹ️  Adicionando configuração do SonarQube ao settings.xml existente..."
        # Adicionar profile do SonarQube
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # macOS
            sed -i '' '/<\/profiles>/i\
    <profile>\
      <id>sonar</id>\
      <properties>\
        <sonar.host.url>http://localhost:9000</sonar.host.url>\
        <sonar.login>'$TOKEN'</sonar.login>\
      </properties>\
    </profile>
' "$SETTINGS_FILE"
        else
            # Linux
            sed -i '/<\/profiles>/i\
    <profile>\
      <id>sonar</id>\
      <properties>\
        <sonar.host.url>http://localhost:9000</sonar.host.url>\
        <sonar.login>'$TOKEN'</sonar.login>\
      </properties>\
    </profile>
' "$SETTINGS_FILE"
        fi
        echo "✅ Configuração adicionada com sucesso!"
    fi
else
    echo "ℹ️  Criando novo arquivo settings.xml..."
    cat > "$SETTINGS_FILE" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 
          http://maven.apache.org/xsd/settings-1.2.0.xsd">
  <profiles>
    <profile>
      <id>sonar</id>
      <properties>
        <sonar.host.url>http://localhost:9000</sonar.host.url>
        <sonar.login>$TOKEN</sonar.login>
      </properties>
    </profile>
  </profiles>
</settings>
EOF
    echo "✅ Arquivo settings.xml criado com sucesso!"
fi

echo ""
echo "✅ Configuração concluída!"
echo "📝 Token configurado em: $SETTINGS_FILE"
echo ""
echo "Agora pode executar:"
echo "  cd zeromonos && ./run-sonar.sh local"

