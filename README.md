# TETRIS
## satorres8 - 1636391
## mscarlos107 - 1631872

---

# **Repositorio Git: CI/CD, Protecciones y Automatización**

Este repositorio implementa un flujo de trabajo robusto para garantizar la calidad del código mediante automatizaciones en GitHub Actions, reglas de protección de ramas y hooks locales. Aquí se describen los pasos configurados hasta el momento.

---

## **Configuraciones implementadas**

### **1. CI Workflow con GitHub Actions**
Un workflow de integración continua (CI) que se activa en:
- **Commits push** en la rama principal (`main`).
- **Pull requests** dirigidos hacia `main`.

Este workflow:
1. Configura un entorno basado en Ubuntu.
2. Instala y verifica la versión de Java y Maven.
3. Compila el proyecto y ejecuta pruebas unitarias.
4. Genera un informe de cobertura de código usando **JaCoCo**.

#### **Archivo del Workflow (`.github/workflows/ci.yml`)**
```yaml
name: CI Workflow

on:
  push:
    branches:
      - main
  pull_request:
    branches:
      - main

jobs:
  build-and-test:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'

      - name: Verify Java version
        run: java --version

      - name: Install Maven
        run: sudo apt-get install -y maven

      - name: Check Maven version
        run: mvn -version

      - name: Build with Maven
        run: mvn clean install

      - name: Run tests
        run: mvn test

      - name: Generate Coverage Report
        run: mvn jacoco:report
```

---

### **2. Bloqueo de Push con Hooks Locales**

Para evitar que se suba código que no pase los tests, se configuró un **hook `pre-push`** en el repositorio local. Este hook:
1. Ejecuta pruebas locales antes de realizar un `git push`.
2. Bloquea el `push` si los tests fallan.

#### **Script del `pre-push` hook**
Este script debe colocarse en `.git/hooks/pre-push` y marcarse como ejecutable.

```bash
#!/bin/bash

echo "Ejecutando tests antes del push..."

# Ejecuta los tests con Maven
mvn clean test

# Verifica el resultado de los tests
if [ $? -ne 0 ]; then
    echo "Los tests fallaron. El push ha sido bloqueado."
    exit 1
fi

echo "Todos los tests pasaron. Procediendo con el push..."
exit 0
```


### **3. Reglas de Protección de Ramas en GitHub**

Se configuraron reglas de protección en la rama principal (`main`) para garantizar la estabilidad del proyecto. Estas reglas incluyen:
- **Requerir status checks para pasar antes de fusionar**:
    - El CI Workflow en GitHub Actions debe ejecutarse y pasar exitosamente.
- **Requerir que las ramas estén actualizadas antes del merge**:
    - Asegura que cualquier rama que intente fusionarse con `main` esté sincronizada.
- **Restringir quién puede realizar push directo a `main`**:
    - Obliga a trabajar con pull requests para realizar cambios en `main`.

