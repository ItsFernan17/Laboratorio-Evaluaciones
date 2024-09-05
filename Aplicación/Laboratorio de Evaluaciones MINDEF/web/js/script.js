function mostrarBotones() {
    var modificar = document.querySelector(".contenedor .botonMenuModificar");
    var anular = document.querySelector(".contenedor .botonMenuAnular");  
    
    if(modificar){
        modificar.style.display="inline";
        anular.style.display="inline";        
    }
}