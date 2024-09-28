#!/bin/bash

# Nombre del archivo de salida
output_file="project_structure.txt"

# Crear o vaciar el archivo de salida
> "$output_file"

# Crear el índice en el archivo de salida
echo "Índice de archivos:" >> "$output_file"

# Buscar archivos de código ignorando los irrelevantes
find . -type f \( -name "*.kt" -o -name "*.xml" -o -name "*.json" \) \
    ! -path "./build/*" \
    ! -path "./.gradle/*" \
    ! -path "./gradle/*" \
    ! -path "./images-md/*" \
    ! -path "*/drawable/*" \
    ! -path "*/intermediates/*" \
    ! -path "*/packaged_res/*" \
    ! -path "*/mipmap-anydpi-v4/*" \
    ! -path "*/androidTest/*" \
    ! -path "*/test/*" \
    ! -path "*/res/values/colors.xml" \
    ! -path "*/res/values/themes.xml" \
    ! -path "*/res/values/strings.xml" \
    ! -path "*/res/values/ids.xml" \
    ! -path "*/res/xml/*" \
    ! -path "*/mipmap-anydpi/*" \
    ! -path "./settings.gradle.kts" \
    ! -path "./.idea/*" \
    ! -path "*/generated/*" \
    ! -path "./app/src/main/AndroidManifest.xml" \
    ! -path "./app/src/main/java/com/example/myaplicacion/ui/theme/*" \
    ! -path "./app/src/main/res/values-v31/themes.xml" \
    ! -name "build.gradle.kts" >> "$output_file"

echo -e "\nContenido de los archivos:\n" >> "$output_file"

# Recorre los archivos de código y añade su contenido al archivo de salida
find . -type f \( -name "*.kt" -o -name "*.xml" -o -name "*.json" \) \
    ! -path "./build/*" \
    ! -path "./.gradle/*" \
    ! -path "./gradle/*" \
    ! -path "./images-md/*" \
    ! -path "*/drawable/*" \
    ! -path "*/intermediates/*" \
    ! -path "*/packaged_res/*" \
    ! -path "*/mipmap-anydpi-v4/*" \
    ! -path "*/androidTest/*" \
    ! -path "*/test/*" \
    ! -path "*/res/values/colors.xml" \
    ! -path "*/res/values/themes.xml" \
    ! -path "*/res/values/strings.xml" \
    ! -path "*/res/values/ids.xml" \
    ! -path "*/res/xml/*" \
    ! -path "*/mipmap-anydpi/*" \
    ! -path "./settings.gradle.kts" \
    ! -path "./.idea/*" \
    ! -path "*/generated/*" \
    ! -path "./app/src/main/AndroidManifest.xml" \
    ! -path "./app/src/main/java/com/example/myaplicacion/ui/theme/*" \
    ! -path "./app/src/main/res/values-v31/themes.xml" \
    ! -name "build.gradle.kts" | while read file; do
    echo "Archivo: $file" >> "$output_file"
    echo "------------------------------------------------------------" >> "$output_file"
    cat "$file" >> "$output_file"
    echo -e "\n" >> "$output_file"
done

echo "Proceso completado. El contenido se ha guardado en $output_file."
