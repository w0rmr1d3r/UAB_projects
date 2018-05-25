<?php

    echo $message;
    if($message == 'Compra realizada correctamente')
    {
        header("Refresh:1; url='http://deic-dc0.uab.cat/~tdiw-j5/controllers/controllerUserPanel.php'");
    }

?>