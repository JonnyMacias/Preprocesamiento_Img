# 🖼️ Preprocesamiento de Imágenes de Números para OCR

Este proyecto implementa un pipeline de preprocesamiento de imágenes enfocado en la lectura y preparación de **números manuscritos o impresos** para su posterior análisis o reconocimiento (OCR).

## 🚀 Características

El sistema toma como entrada una imagen que contiene un número, y realiza los siguientes pasos secuenciales:

1. 🎨 **Conversión a escala de grises**  
   Elimina la información de color para reducir complejidad y centrarse en la forma.

2. 📦 **Caja de mínima inclusión (Bounding Box)**  
   Detecta y recorta la región exacta donde se encuentra el número, eliminando bordes innecesarios.

3. 📐 **Normalización del aspect ratio**  
   Ajusta la imagen para mantener las proporciones del número, evitando deformaciones.

4. 📏 **Redimensionamiento (scaling)**  
   Escala la imagen a un tamaño estándar requerido por el modelo o procesamiento posterior.

5. ⚫ **Binarización**  
   Convierte la imagen a blanco y negro para facilitar la detección de contornos.

6. 🔍 **Extracción de contornos**  
   Detecta los contornos principales del número para obtener una representación estructural.

7. 🧊 **Generación de corpus 4D**  
   Prepara la imagen como tensor de 4 dimensiones para uso en modelos de deep learning:
   
## 💾 Salidas generadas

- 🖼️ **Imagen del contorno** del número procesado, útil para visualización o análisis estructural.
- 📄 **Archivo `.txt`** con los datos del tensor 4D correspondiente a la imagen preprocesada.
