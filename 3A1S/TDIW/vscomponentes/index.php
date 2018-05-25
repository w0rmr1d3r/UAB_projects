<?php
    session_start();
    if(!isset($_SESSION['detalle']))
    {
        $_SESSION['detalle'] = [];
    }
?>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>VS-components</title>
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/styles.css">

        <script src="js/jquery.js"></script>
        <script src="js/functions.js"></script>
    </head>
    <body>

        <?php
           require_once("controllers/controllerIndex.php");
        ?>

        <footer class="container-fluid">
            <div class="row hidden-xs">
                <section class="col-xs-offset-1 col-xs-5 col-sm-offset-1 col-sm-5 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2 navbar">
                    <h3 class="text-justify">POR QUÉ COMPRAR</h3>
                    <ul class="nav">
                        <li><a href="#">Formas de pago</a></li>
                        <li><a href="#">Gastos de envío</a></li>
                        <li><a href="#">Preguntas frecuentes</a></li>
                    </ul>
                </section>

                <section class="col-xs-offset-1 col-xs-5 col-sm-offset-1 col-sm-5 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2 navbar">
                    <h3 class="text-justify">QUIÉNES SOMOS</h3>
                    <ul class="nav">
                        <li><a href="#">Quiénes somos</a></li>
                        <li><a href="#">Condiciones de compra</a></li>
                        <li><a href="#">Garantías</a></li>
                        <li><a href="#">Fabricantes</a></li>
                    </ul>
                </section>

                <section class="col-xs-offset-1 col-xs-5 col-sm-offset-1 col-sm-5 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2 navbar">
                    <h3 class="text-justify">CONTACTARR</h3>
                    <ul class="nav">
                        <li><a href="#">Centro de soporte</a></li>
                        <li><a href="#">Aviso legal</a></li>
                        <li><a href="#">Privacidad</a></li>
                        <li><a href="#">Política de cookies</a></li>
                    </ul>
                </section>

                <section class="col-xs-offset-1 col-xs-5 col-sm-offset-1 col-sm-5 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2 navbar">
                    <h3 class="text-justify">OTROS</h3>
                    <ul class="nav">
                        <li><a href="#">Tarjetas regalo</a></li>
                        <li><a href="#">Black Friday</a></li>
                    </ul>
                </section>
            </div>

            <div class="row container-section">
                <p class="col-xs-12 col-sm-12 col-md-12 col-lg-12 text-center copyright"><span class="glyphicon glyphicon-copyright-mark"></span> tdiw-j5, Héctor De Armas and Ramón Guimera</p>
            </div>
        </footer>
    </body>
</html>
