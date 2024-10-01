
function foco(idElemento) {
    document.getElementById(idElemento).focus();
}

function habilitarGuardar(idElemento, idElemento2) {
    document.getElementById(idElemento).style.display='block';
    document.getElementById(idElemento2).style.display='none';
}

function habilitarModificar(idElemento, idElemento2) {
    document.getElementById(idElemento).style.display = 'none';
    document.getElementById(idElemento2).style.display = 'block';
}

function ocultar(idElemento, idElemento2) {
    document.getElementById(idElemento).style.display = 'none';
    document.getElementById(idElemento2).style.display = 'none';
}

function desplazarse() {
    const formElement = document.getElementById('form');
    if (formElement) {
        formElement.scrollIntoView({ behavior: 'smooth' });
    }
}


