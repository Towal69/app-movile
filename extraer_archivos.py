import os

# Nombre del archivo de salida
output_file = "archivos.txt"

# Crear o vaciar el archivo de salida
with open(output_file, "w") as file:
    file.write("Índice de archivos:\n")

# Función para excluir ciertos archivos y directorios
def exclude_path(path):
    excluded_patterns = [
        "./build/*",
        "./images-md/*",
        "*/drawable/*",
        "*/intermediates/*",
        "*/packaged_res/*",
        "*/mipmap-anydpi-v4/*",
        "*/androidTest/*",
        "*/test/*",
        "*/res/values/colors.xml",
        "*/res/values/themes.xml",
        "*/res/values/strings.xml",
        "*/res/values/ids.xml",
        "*/res/xml/*",
        "*/mipmap-anydpi/*",
        "./settings.gradle.kts",
        "./app/src/main/AndroidManifest.xml",
        "./app/src/main/java/com/example/myaplicacion/ui/theme/*",
        "./app/src/main/res/values-v31/themes.xml",
        "build.gradle.kts",
        ".idea/*",
        "*/generated/*"
    ]

    for pattern in excluded_patterns:
        if path.startswith(pattern):
            return True
    return False

# Buscar archivos de código y añadir su contenido al archivo de salida
for root, dirs, files in os.walk("."):
    for file in files:
        if file.endswith(".kt") or file.endswith(".xml") or file.endswith(".json"):
            file_path = os.path.join(root, file)
            if not exclude_path(file_path):
                with open(output_file, "a") as file:
                    file.write(f"Archivo: {file_path}\n")
                    file.write("------------------------------------------------------------\n")
                    with open(file_path, "r") as content_file:
                        file.write(content_file.read())
                    file.write("\n")

print("Proceso completado. El contenido se ha guardado en", output_file)