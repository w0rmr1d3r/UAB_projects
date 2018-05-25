<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>VS-components</title>
        <link rel="stylesheet" href="../css/bootstrap.css">
        <link rel="stylesheet" href="../css/styles.css">

        <script src="../js/jquery.js"></script>
        <script src="../js/functions.js"></script>
    </head>
    <body>
        <header class="container-fluid">
            <div class="row">
                <a href="/~tdiw-j5/" name="url" onload="getURL();" class="col-xs-3 col-sm-3 col-md-2 col-lg-2"><img src="../images/logo.png" alt="" class="img-responsive img-rounded"></a>
                <img src="../images/logo_entregas_home.png" alt="" class="img-responsive img-rounded col-xs-offset-1 col-xs-3 col-sm-offset-1 col-sm-3 col-md-offset-1 col-md-2 col-lg-offset-1 col-lg-2 img-entregas">
                <img src="../images/logo_envios_home.png" alt="" class="img-responsive img-rounded col-xs-offset-2 col-xs-3 col-sm-offset-2 col-sm-3  col-md-offset-2 col-md-2 col-lg-offset-2 col-lg-2 img-envios">
            </div>

            <div class="row">
                <nav class="navbar-default navPrincipal">
                    <!-- Toogle navitation sirve para tener un boton con menu desplegable al encontrarnos en una pantalla de movil-->
                    <div class="navbar-header">
                      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                        <!-- iconos de barra dentro del boton -->
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                      </button>
                      <!-- este enlace no se mete dentro del toogle navigation al entrar en una pantalla movil -->
                      <a href="/~tdiw-j5/" name="url" onload="getURL();" class="navbar-brand">VS-components</a>
                    </div>

                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                      <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">Components</a>
                            <ul class="dropdown-menu">
                                <li><a href="/components/placas-base">Placas Base</a></li>

                                <li><a href="/components/procesadores">Procesadores</a></li>

                                <li><a href="/components/discos-duros">Discos Duros</a></li>

                                <li><a href="/components/tarjetas-graficas">Tarjetas Gráficas</a></li>

                                <li><a href="/components/memorias-ram">Memoria RAM</a></li>

                                <li><a href="/components/grabadoras-dvd-blu-ray">Grabadoras DVD/Blu-Ray</a></li>

                                <li><a href="/components/disqueteras">Disqueteras</a></li>

                                <li><a href="/components/tarjetas-sonido">Tarjetas de Sonido</a></li>

                                <li><a href="/components/torres">Torres/Cajas/Carcasas</a></li>

                                <li><a href="/components/ventiladores">Ventiladores</a></li>

                                <li><a href="/components/fuentes-alimentacion">Fuentes Alimentación</a></li>
                            </ul>
                        </li>

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">Accesories</a>
                            <ul class="dropdown-menu">
                                <li><a href="/accesories/gamepads">Gamepads</a></li>

                                <li><a href="/accesories/joysticks">Joysticks</a></li>

                                <li><a href="/accesories/capturadoras">Capturadoras</a></li>

                                <li><a href="/accesories/altavoces">Altavoces</a></li>

                                <li><a href="/accesories/microfonos">Micrófonos</a></li>
                            </ul>
                        </li>

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">Laptops</a>
                            <ul class="dropdown-menu">
                                <li><a href="/laptops/portatiles">Portátiles</a></li>

                                <li><a href="/laptos/ultrabooks">Ultrabooks</a></li>

                                <li><a href="/laptops/sobremesa">Sobremesa</a></li>

                                <li><a href="/laptops/todo-en-1">Ordenadores todo en uno</a></li>
                            </ul>
                        </li>

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">Peripherics</a>
                            <ul class="dropdown-menu">
                                <li><a href="peripherics/monitores-pc">Monitores</a></li>

                                <li><a href="peripherics/impresoras">Impresoras</a></li>

                                <li><a href="peripherics/impresoras-3d">Impresoras 3D</a></li>

                                <li><a href="peripherics/altavoces">Altavoces</a></li>

                                <li><a href="peripherics/teclados">Teclados</a></li>

                                <li><a href="peripherics/ratones">Ratones</a></li>

                                <li><a href="peripherics/auriculares">Auriculares</a></li>

                                <li><a href="peripherics/webcam">Webcams</a></li>

                                <li><a href="peripherics/gamepads-joysticks">Gamespads/Joysticks</a></li>

                                <li><a href="peripherics/microfonos">Micrófonos</a></li>

                                <li><a href="peripherics/alfombrillas">Alfombrillas</a></li>

                                <li><a href="peripherics/gadgets">Gadgets</a></li>
                            </ul>
                        </li>

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">Smartphones</a>
                            <ul class="dropdown-menu">
                                <li><a href="/smartphone-moviles/acer">Acer</a></li>

                                <li><a href="/smartphone-moviles/apple">Apple</a></li>
                                <li><a href="/smartphone-moviles/asus">Asus</a></li>
                                <li><a href="/smartphone-moviles/bq">Bq</a></li>
                                <li><a href="/smartphone-moviles/honor">Honor</a></li>
                                <li><a href="/smartphone-moviles/htc">HTC</a></li>
                                <li><a href="/smartphone-moviles/huawei">Huawei</a></li>
                                <li><a href="/smartphone-moviles/lg">LG</a></li>
                                <li><a href="/smartphone-moviles/meizu">Meizu</a></li>
                                <li><a href="/smartphone-moviles/motorola">Motorola</a></li>
                                <li><a href="/smartphone-moviles/nokia">Nokia</a></li>
                                <li><a href="/smartphone-moviles/samsung">Samsung</a></li>
                                <li><a href="/smartphone-moviles/sony">Sony</a>
                                <li><a href="/smartphone-moviles/xiaomi">Xiaomi</a></li>
                                <li><a href="/smartphone-moviles/zte">ZTE</a></li>  
                            </ul>
                        </li>

                        <li>
                            <button type="submit" class="btn btnLogin" onclick="window.location.href='../controllers/controllerLogin.php'"><span class="glyphicon glyphicon-user"></span>&nbsp;Log in</button>
                        </li>

                        <li>
                            <form class="navbar-form navbar-left">
                                <div class="form-group">
                                  <input type="text" name="search" class="form-control search" placeholder="Search product">
                                </div>
                            </form>
                        </li>
                        
                        <li class="li-dropdown">
                            <a href="#"><span class="glyphicon glyphicon-shopping-cart"></span>&nbsp;My cart <span class="product-count"><?php echo count($sessionDetalle); ?></span></a>
                            <div id="viewCart" class="ul-dropdown-content">
                                <?php require("../controllers/controllerCart.php"); ?>
                            </div>
                        </li>
                      </ul>
                    </div><!-- /.navbar-collapse -->
                </nav>
            </div>
        </header>

        <section class="container-fluid section-register">
            <div class="row">
                <form method="POST" id="formRegister" class="form-horizontal col-xs-offset-1 col-xs-10 col-sm-offset-1 col-sm-4 col-md-offset-2 col-md-3 col-lg-offset-2 col-lg-3 thumbnail">
                    <h4 class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8 text-center h4-register">Register</h4>
                    <h5 class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8 text-left text-danger">* required field.</h5>
                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                            <input type="text" class="form-control" name="name" id="name" placeholder="* Name" required="true">
                        </div>
                    </div>

                   <div id="confirmUsername">
                       <div class="form-group">
                            <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                                <input type="text" class="form-control" name="username" id="username" placeholder="* Username" onblur="validateInputField('username', this.value, 'confirmUsername')" required="true">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                            <input type="password" class="form-control" name="password" id="password" placeholder="* Password" minlength="8" required="true">
                        </div>
                    </div>

                   <div id="confirmEmail">
                        <div class="form-group">
                            <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                                <input type="email" class="form-control" name="email" id="email" placeholder="* Email" onblur="validateInputField('email', this.value, 'confirmEmail')" required="true">
                            </div>
                        </div>
                   </div>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                            <input type="tel" class="form-control" name="phone" id="phone" placeholder="* Phone" maxlength="9" minlength="9" required="true">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                            <input type="text" class="form-control" name="street" id="street" placeholder="* Street" required="true">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                            <input type="text" class="form-control" name="city" id="city" placeholder="* City" required="true">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                            <input type="text" class="form-control" name="postal" id="postal" placeholder="* Postal Code" maxlength="5" minlength="5" required="true">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                            <input type="text" class="form-control" name="card" id="card" placeholder="* Credit Card" maxlength="16" minlength="16" required="true">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-2 col-xs-10 col-sm-offset-2 col-sm-10">
                            <input type="button" class="btn btn-success" onclick="validateRegisterForm();" value="Sign up">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <div class="col-xs-offset-1 col-xs-10" id="confirmation">
                            
                        </div>
                    </div>
                </form>

                <aside>
                    <div>
                        <form method="POST" id="formLogin" class="col-xs-offset-1 col-xs-10 col-sm-offset-2 col-sm-4 col-md-offset-2 col-md-3 col-lg-offset-2 col-lg-3 thumbnail form-horizontal">
                            <h4 class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8 text-center h4-register">Login</h4>
                            <div class="form-group">
                                <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                                    <input type="text" class="form-control" name="username" id="usernameLogin" placeholder="Username" required="true">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-xs-offset-2 col-xs-8 col-sm-offset-2 col-sm-8 col-md-offset-2 col-md-8 col-lg-offset-2 col-lg-8">
                                    <input type="password" class="form-control" name="password" id="passwordLogin" placeholder="Password" required="true">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-xs-offset-2 col-xs-10 col-sm-offset-2 col-sm-10">
                                    <input type="button" onclick="validateLoginForm();" class="btn btn-success" value="Sign in">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <div class="col-xs-offset-1 col-xs-10" id="Wcredentials">

                                </div>
                            </div>
                        </form>
                    </div>
                </aside>
            </div>
        </section>

        <footer class="footer container-fluid">
            <div class="row">
                <p class="col-xs12 col-sm-12 col-md-12 col-lg-12 text-center copyright"><span class="glyphicon glyphicon-copyright-mark"></span> tdiw-j5, Héctor De Armas and Ramón Guimera</p>
            </div>
        </footer>
    </body>
</html>