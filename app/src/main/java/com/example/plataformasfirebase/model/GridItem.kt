package com.example.plataformasfirebase.model

class GridItem {

    // Declaración de variables

    var image:Int ? = 0
    var description:String ? = null

    // Constructor

    constructor(image: Int?, description: String?) {
        this.image = image
        this.description = description
    }
}