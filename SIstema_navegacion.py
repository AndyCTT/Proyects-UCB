#AUTOR: BURGOA CALLEJAS ANDY JORGE
#FECHA: 31/05/2025
#VERSION: 1.3
import os
# SE AÑADEN LOS BLOQUE Y SUS DESCRIPSIONES EN DICCIONARIOS
BLOQUES = {
    "A": ["Coliseo U.C.B.", "Departamento de Deporte", "Club. U Catolica", "Sala de Danza"],
    "B": ["Departamento de Cultura y Arte", "Sala de Literatura", "Extencion Cultural", "Unidad de Auditoria Interna"],
    "C": ["Laboratoria de Ingenieria", "Sala de Docentes", "Laboratorios de Computacion Aplicada", "Redes y Soporte"],
    "D": ["Aulas D1 a D30", "Auditorios 2-3-4", "Laboratorio de Computacion 4", "Laboratorio de Redes 5", 
          "Laboratorio de Psicofisiologia", "Paraninfo U.C.B", "Capilla (San Pablo)", 
          "Sala de Docentes (Facultad de Ingenieria)", "Sala de Docentes (Ciencias Basicas)",
          "Servicio y Mantenimiento", "Archivo Nacional", "Central Telefonica", "Laboratorio CINAES", "Centro de Computo"],
    "F": ["Registro Academico", "Decanura de la Facultad de Derecho", "Carrera de Ciencias Politicas", 
          "Carrera de Derecho", "Oficinas de Postgrado de Derecho", "Institutos Para la Democracia(lpD)", 
          "Aulas F1 a F13", "Laboratorios 1 y 7"],
    "K": ["Oficina de Informaciones", "Departamento Marketing y Comunicacion", "Departamento Admision y Orientacion",
          "Marketing Postgrado", "Departamento de Pastoral Universitaria", "Cato Voluntarios",
          "Departamento de Personal", "Centro de Investigacion de Turismo"],
    "AGORA": ["Espacio Cultural", "Zona de descanso"],
    "BIBLIOTECA": ["Sala de Lectura", "Sala de Computadoras", "Archivo General"]
}
#SE AÑADE LOS PASOS EN DICCIONARIOS DE NAVEGACION PARA TODAS LA COMBINACIONES POSIBLES
RUTAS = {
    "A": {
        "B": [
            "Sal del coliseo y avanza por el pasillo a tu izquierda",
            "Al terminar el pasillo da medio giro hacia la izquierda y avanza de frente"
        ],
        "C": [
            "Sal del coliseo y avanza por el pasillo a tu izquierda",
            "Al terminar el pasillo avanza de frente"
        ],
        "D": [
            "Sal del coliseo y avanza por el pasillo a tu derecha",
            "Al llegar al final notaras que las AULAS D-32,D31 estan a la izquierda"
        ],
        "F": [
            "Sal del coliseo y avanza por el pasillo a tu izquierda",
            "Al terminar el pasillo baja las escaleras",
            "A tu izquierda encontraras el bloque F-1 y si avanzas unos pasos mas encontraras el bloque F-2"
        ],
        "K": [
            "Sal del coliseo y avanza por el pasillo a tu derecha",
            "Al llegar al final gira a la izquierda y continua hacia adelante",
            "Gira a la derecha y luego a la izquierda",
            "Continua bajando las escaleras hasta el final, camina hacia la entrada y mira hacia atras"
        ],
        "AGORA": [
            "Sal del coliseo y avanza por el pasillo a tu izquierda",
            "Baja las escaleras y a tu derecha se encuentra la entrada del AGORA"
        ],
        "BIBLIOTECA": [
            "Sal del coliseo y avanza por el pasillo a tu izquierda",
            "Al terminar el pasillo baja las escaleras",
            "A tu izquierda encontraras el bloque F, avanza un poco y veras un pasadizo que te llevara hacia la BIBLIOTECA"
        ]
    },
    "B": {
        "A": [
            "Sal y da medio giro hacia la derecha y avanza de frente",
            "Avanza el pasillo hasta la primera o segunda puerta del edificio de la derecha"
        ],
        "C": [
            "Sal del bloque y camina hacia tu izquierda"
        ],
        "D": [
            "Sal y da medio giro hacia la derecha y avanza de frente",
            "Avanza de frente y al llegar al final notaras que las AULAS D-32,D31 estan a la izquierda"
        ],
        "F": [
            "Sal del bloque y baja las escaleras",
            "Al final de las escaleras mira a tu izquierda, encontraras el bloque F-1 y si avanzas unos pasos mas encontraras el bloque F-2"
        ],
        "K": [
            "Sal del bloque y baja las escaleras",
            "Da medio giro a la derecha y continua por el camino",
            "Sigue bajando hacia la entrada de la universidad",
            "Camina hacia la entrada y mira hacia atras"
        ],
        "AGORA": [
            "Sal del bloque y baja las escaleras y gira hacia la derecha"
        ],
        "BIBLIOTECA": [
            "Sal del bloque y baja las escaleras",
            "A tu izquierda encontraras el bloque F, avanza un poco y veras un pasadizo que te llevara hacia la BIBLIOTECA"
        ]
    },
    "C": {
        "A": [
            "Sal del bloque, da medio giro hacia la izquierda y sigue de frente",
            "Avanza el pasillo hasta la primera o segunda puerta del edificio de la derecha"
        ],
        "B": [
            "Sal del bloque y camina hacia tu derecha"
        ],
        "D": [
            "Sal y da medio giro hacia la izquierda y avanza de frente",
            "Al llegar al final notaras que las AULAS D-32,D31 estan a la izquierda"
        ],
        "F": [
            "Sal del bloque y baja las escaleras",
            "Al final de las escaleras mira a tu izquierda, encontraras el bloque F-1 y si avanzas unos pasos mas encontraras el bloque F-2"
        ],
        "K": [
            "Sal del bloque y baja las escaleras",
            "Da medio giro a la derecha y continua por el camino",
            "Sigue bajando hacia la entrada de la universidad",
            "Camina hacia la entrada y mira hacia atras"
        ],
        "AGORA": [
            "Sal del bloque y baja las escaleras y gira hacia la derecha"
        ],
        "BIBLIOTECA": [
            "Sal del bloque y baja las escaleras",
            "A tu izquierda encontraras el bloque F, avanza un poco y veras un pasadizo que te llevara hacia la BIBLIOTECA"
        ]
    },
    "D": {
        "A": [
            "Sal del bloque, por la parte de atras y sigue de frente",
            "Avanza el pasillo hasta la primera o segunda puerta del edificio de la izquierda"
        ],
        "B": [
            "Sal del bloque y camina hacia el frente",
            "Al final del pasillo da medio giro hacia izquierda y avanza"
        ],
        "C": [
            "Sal del bloque y avanza de frente hasta el final del pasillo"
        ],
        "F": [
            "Sal del bloque y avanza de frente hasta el final del pasillo",
            "Baja las escaleras",
            "Al final de las escaleras mira a tu izquierda, encontraras el bloque F-1 y si avanzas unos pasos mas encontraras el bloque F-2"
        ],
        "K": [
            "Sal del bloque por el ascensor",
            "Gira hacia atras y avanza por el camino",
            "Baja las escaleras hacia la entrada de la universidad",
            "Camina hacia la entrada y mira hacia atras"
        ],
        "AGORA": [
            "Sal del bloque y sigue de frente hasta la primera puerta de la derecha"
        ],
        "BIBLIOTECA": [
            "Sal del bloque y camina de frente por el pasillo",
            "Baja hasta el final de las escaleras",
            "A tu izquierda encontraras el bloque F, avanza un poco y veras un pasadizo que te llevara hacia la BIBLIOTECA"
        ]
    },
    "F": {
        "A": [
            "Sal del bloque y sube las escaleras",
            "Entra a el pasillo y ve de frente hasta la primera o segunda puerta"
        ],
        "B": [
            "Sal del bloque y sube las escaleras",
            "Al final sigue adelante"
        ],
        "C": [
            "Sal del bloque y sube las escaleras y gira hacia la derecha"
        ],
        "D": [
            "Sal del bloque y sube las escaleras",
            "Gira hacia la izquierda y continua por el pasillo",
            "Al final del pasillo encontraras las aulas D-31,D32 ubicados hacia la izquierda"
        ],
        "K": [
            "Sal del bloque y sigue el camino de bajada",
            "Continua bajando hasta llegar a la entrada",
            "Camina hacia la entrada y mira hacia atras"
        ],
        "AGORA": [
            "Sal del bloque y sigue de frente subiendo un poco las escaleras"
        ],
        "BIBLIOTECA": [
            "Sal del bloque y camina por el pasadizo"
        ]
    },
    "K": {
        "A": [
            "Sal del bloque y sube las escaleras",
            "Sigue el camino hacia la izquierda",
            "Sigue de frente y gira a la derecha",
            "Gira a la izquierda, luego a la derecha",
            "Camina por el pasillo hasta la primera o segunda puerta a la izquierda"
        ],
        "B": [
            "Sal del bloque y sube las escaleras",
            "Sigue el camino hacia la izquierda",
            "Sigue de frente y gira a la derecha",
            "Gira a la izquierda, luego a la derecha",
            "Camina por el pasillo hasta el final y gira a la izquierda"
        ],
        "C": [
            "Sal del bloque y sube las escaleras",
            "Sigue el camino hacia la izquierda",
            "Sigue de frente y gira a la derecha",
            "Gira a la izquierda, luego a la derecha",
            "Camina por el pasillo hasta el final y sigue de frente"
        ],
        "D": [
            "Sal del bloque y sube las escaleras",
            "Sigue el camino hacia la izquierda",
            "Sigue de frente"
        ],
        "F": [
            "Sal del bloque y sube la rampa",
            "Sigue el camino hacia la izquierda",
            "Sigue de frente y gira a la izquierda",
            "Camina en linea recta hasta llegar al bloque F"
        ],
        "AGORA": [
            "Sal del bloque y sube las escaleras",
            "Sigue el camino hacia la izquierda",
            "Sigue de frente y gira a la derecha",
            "Camina en linea recta hasta el final",
            "Gira hacia la izquierda"
        ],
        "BIBLIOTECA": [
            "Sal del bloque y sube la rampa",
            "Sigue el camino de la derecha",
            "Sigue de frente hasta llegar a la BIBLIOTECA"
        ]
    },
    "AGORA": {
        "A": [
            "Sal del bloque y sube las escaleras",
            "Sigue el camino hacia la izquierda",
            "Sigue de frente y gira a la derecha"
        ],
        "B": [
            "Sal del bloque y sube las escaleras",
            "Sigue el camino en linea recta"
        ],
        "C": [
            "Sal del bloque y sube las escaleras",
            "Sigue de frente y gira a la derecha"
        ],
        "D": [
            "Sal del bloque y gira a la derecha",
            "Sigue el camino hacia la derecha",
            "Sigue de frente"
        ],
        "F": [
            "Sal del bloque y camina hacia media derecha"
        ],
        "K": [
            "Sal del bloque y baja las escaleras",
            "Sigue el camino hacia la derecha",
            "Sigue de frente y baja las gradas hacia la izquierda",
            "Continua bajando hasta la entrada",
            "Una vez en la entrada gira hacia atras"
        ],
        "BIBLIOTECA": [
            "Sal del bloque y baja las escaleras hacia media izquierda",
            "Sigue el pasadizo de frente hasta llegar a la biblioteca"
        ]
    },
    "BIBLIOTECA": {
        "A": [
            "Sal de la biblioteca por el 2º piso",
            "Sigue el pasadizo y camina hacia media izquierda",
            "Sigue de frente y sube las escaleras hasta el final",
            "Camina a la izquierda por el pasillo"
        ],
        "B": [
            "Sal de la biblioteca por el 2º piso",
            "Sigue el pasadizo y camina hacia media izquierda",
            "Sigue de frente y sube las escaleras hasta el final"
        ],
        "C": [
            "Sal de la biblioteca por el 2º piso",
            "Sigue el pasadizo y camina hacia media izquierda",
            "Sigue de frente, sube las escaleras hasta el final y gira a la derecha"
        ],
        "D": [
            "Sal de la biblioteca por el 2º piso",
            "Sigue el pasadizo y camina hacia media izquierda",
            "Sigue de frente, sube las escaleras hasta el final y gira a la izquierda",
            "Sigue de frente hasta el final del pasillo"
        ],
        "F": [
            "Sal del bloque por el 2º piso"
        ],
        "K": [
            "Sal del bloque por la planta baja",
            "Sigue el camino en linea recta",
            "Continua caminando hasta la entrada",
            "Gira a la derecha"
        ],
        "AGORA": [
            "Sal de la biblioteca por el 2º piso",
            "Sigue el pasadizo y camina hacia media izquierda",
            "Sigue de frente"
        ]
    }
}

# SE AÑADEN LAS FUNCIONES QUE EL PROGRAMA NECESITARA


# FUNCION PARA LIMPIA LA PANTALLA USANDO LA LIBRERIA IMPORT OS

def limpiar_pantalla():
    
    os.system('cls' if os.name == 'nt' else 'clear')
    
# FUNCION PARAMOSTRAR EL MENU DE BLOQUES

def mostrar_menu():
    print("\n-- MENÚ DE BLOQUES --")
    print("|  BLOQUE A  |  BLOQUE B  |")
    print("|  BLOQUE C  |  BLOQUE D  |")
    print("|  BLOQUE F  |  BLOQUE K  |")
    print("|   AGORA    | BIBLIOTECA |\n")
    
# FUNCION PARA IMPRIMIR LA DESCRIPSION DEL BLOQUE ELEGIDO 

def descripcion(bloque):
    print(f"\nDescripción del BLOQUE {bloque}:")
    for ambiente in BLOQUES[bloque]:
        print(f"- {ambiente}")
        
#FUNCION PARA NAVEGAR ENTRE LOS BLOQUES

def navegar(origen, destino):
    if origen == destino:         # SI EL ORIGEN ES IGUAL AL DESTINO SE IMPRIME LA DESCRIPSIOON
        print(f"¡Ya te te encuentras en el BLOQUE {origen}!")
        descripcion(origen)
        return
    if destino in RUTAS.get(origen, {}): # SI EL DESTINO ESTA EN DICIONARIO DE RUTAS IMPRIME LOS PASOS CON UN FOR 
        print("\n__PASOS__")
        paso_numero = 1
        for paso in RUTAS[origen][destino]:
            print()
            print(f"{paso_numero}: {paso}")
            print()
            paso_numero += 1
            input("Enter para continuar.") # SE PIDE QUE PRESIONE ENTER PARA CONTINUAR CON EL SIGUIENTE PASO
        print(f"\n¡Felicidades llegaste al BLOQUE {destino}!")
        descripcion(destino)
        
# SE EJECUTA UN WHILE TRUE PARA QUE EL PROGRAMA CORRA HASTA QUE SE CORTE CON UN BREAK
 
while True:
    limpiar_pantalla() # SE USA PARA LIMPIAR LA PANTALLA 
    mostrar_menu()
    origen = input("¿Dónde te encuentras?: ").upper()
    if origen in BLOQUES:   # SI EL ORIGEN ESTA EN LOS BLOQUES
        limpiar_pantalla()   # SE LIMPIA LA PANTALLA
        mostrar_menu()       # SE MUESTRA EL MENU
        destino = input("¿A dónde te diriges?: ").upper()    # SE PIDE QUE ELIJA SU DESTINO 
        if destino in BLOQUES:  # Y SI EL DESTINO ESTA EN LOS BLOQUES
            navegar(origen, destino)  # SE INICIA LA FUNCION DE NAVEGACIO
        break  # SE CORTA EL PROGRAMA O SE PUEDE ELIMINAR EL BREAK PARA QUE EL PROGRAMA SIGA EJECUTANDOSE