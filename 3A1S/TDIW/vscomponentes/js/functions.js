/**
 * Function that builds an xmlhttp object deppending on the browser type
 * @return an xmlhttp object.
 */
function getXMLHTTP() {
    var xmlhttp;

    if (window.XMLHttpRequest) {
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    } else {
        // code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlhttp;
}

/**
 * Gets the URL
 */
function getURL()
{
    document.links["url"].href = "http://deic-dc0.uab.cat/";
}

/**
 * Gets the absolute URL
 * @return absolute URL
 */
function getAbsoluteURL()
{
    return "http://deic-dc0.uab.cat/~tdiw-j5/";
}

/**
 * Loads the products by subCategoryID
 * @param subCategoryID
 */
function loadProducts(subCategoryID)
{
	var xmlhttp = getXMLHTTP();

	/* What to do when we get the asynchormous response from the server */
	xmlhttp.onreadystatechange = function()
  {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
    {
        document.getElementById("sectionProduct").innerHTML = xmlhttp.responseText;
    }
	};

	/* Sending the asynchronous request to the server */
	xmlhttp.open("GET", "./controllers/controllerCategoryProducts.php?product=" + subCategoryID, true);
	xmlhttp.send();
}


function validateLoginForm()
{
  var xmlhttp   = getXMLHTTP();
  var formLogin = new FormData(document.getElementById("formLogin"));

	/* What to do when we get the asynchormous response from the server */
	xmlhttp.onreadystatechange = function()
  {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
    {
      $response = xmlhttp.responseText;
      if ($response == "success")
      {
          window.location.href="controllerUserPanel.php";
      }
      else
      {
          document.getElementById("Wcredentials").innerHTML = xmlhttp.responseText;
      }
    }
 };

    /* Sending the asynchronous request to the server */
    // access
	xmlhttp.open("POST", "../controllers/controllerAccess.php", true);
  xmlhttp.send(formLogin);
}


function validateRegisterForm()
{
    var xmlhttp = getXMLHTTP();
    var formRegister = new FormData(document.getElementById("formRegister"));

	/* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
	xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("confirmation").innerHTML = xmlhttp.responseText;
		}
	};

    /* Sending the asynchronous request to the server */
	xmlhttp.open("POST", "../controllers/controllerRegister.php", true);
    xmlhttp.send(formRegister);
}


// AJAX code to check input field values when onblur event triggerd.
function validateInputField(field, query, divId)
{
    var xmlhttp = getXMLHTTP();

    /* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
    xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById(divId).innerHTML = xmlhttp.responseText;
        }
    };

    xmlhttp.open("GET", "../controllers/controllerValidationInput.php?field=" + field + "&query=" + query, true);
    xmlhttp.send();
}


function validateUpdateData()
{
    var xmlhttp = getXMLHTTP();
    var formUserData = new FormData(document.getElementById("formUserData"));

    /* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
    xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("confirmation").innerHTML = xmlhttp.responseText;
        }
    };

    /* Sending the asynchronous request to the server */
    xmlhttp.open("POST", "../controllers/controllerUpdateUserData.php", true);
    xmlhttp.send(formUserData);
}


function showTable(tableID)
{
    var xmlhttp = getXMLHTTP();

    /* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
    xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("table").innerHTML = xmlhttp.responseText;
        }
    };

    /* Sending the asynchronous request to the server */
    xmlhttp.open("GET", "../../controllers/admin/controllerTables.php?table=" + tableID, true);
    xmlhttp.send();
}


function loadSubcategory(categoryID)
{
  var xmlhttp = getXMLHTTP();
  //var degressTag = document.getElementById("degress");

  /* What to do when we get the asynchormous response from the server */
  xmlhttp.onreadystatechange = function()
  {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
    {
      document.getElementById("subcategory").innerHTML = xmlhttp.responseText;
    }
  };

  /* Sending the asynchronous request to the server */
  xmlhttp.open("GET", "../../controllers/admin/controllerGetSubcategory.php?catgid="+categoryID, true);
  xmlhttp.send();
}


function createNewProduct()
{
    var xmlhttp = getXMLHTTP();
    var data = new FormData(document.getElementById("formPost"));

    /* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
    xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("confirmation").innerHTML = xmlhttp.responseText;
        }
    };

    /* Sending the asynchronous request to the server */
    xmlhttp.open("POST", "../../controllers/admin/controllerCreateProduct.php", true);
    xmlhttp.send(data);
}


function loadListProducts(subCategoryID)
{
	var xmlhttp = getXMLHTTP();

	/* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
	xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("products").innerHTML = xmlhttp.responseText;
		}
	};

	/* Sending the asynchronous request to the server */
	xmlhttp.open("GET", "../../controllers/admin/controllerGetProducts.php?subcategory=" + subCategoryID, true);
	xmlhttp.send();
}


function loadProductData(productID)
{
    var xmlhttp = getXMLHTTP();

	/* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
	xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("divProduct").innerHTML = xmlhttp.responseText;
		}
	};

	/* Sending the asynchronous request to the server */
	xmlhttp.open("GET", "../../controllers/admin/controllerDataProduct.php?product=" + productID, true);
	xmlhttp.send();
}


function editOneProduct()
{
    var xmlhttp = getXMLHTTP();
    var data = new FormData(document.getElementById("formPost"));

    /* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
    xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("confirmation").innerHTML = xmlhttp.responseText;
        }
    };

    /* Sending the asynchronous request to the server */
    xmlhttp.open("POST", "../../controllers/admin/controllerEditProduct.php", true);
    xmlhttp.send(data);
}


function deleteOneProduct()
{
    var xmlhttp = getXMLHTTP();

    /* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
    xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("confirmation").innerHTML = xmlhttp.responseText;
        }
    };

    /* Sending the asynchronous request to the server */
    xmlhttp.open("POST", "../../controllers/admin/controllerDeleteProduct.php", false);
    xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xmlhttp.send(getProductData());
}

function getProductData()
{
    var productID = document.getElementById("products");

    return "products=" + encodeURIComponent(productID.value);
}


// CARRITO DE COMPRAS

function productToCart(option, value)
{
    var xmlhttp = getXMLHTTP();

	/* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
	xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("viewCart").innerHTML = xmlhttp.responseText;
		}
	};

	/* Sending the asynchronous request to the server */
	xmlhttp.open("GET", getAbsoluteURL() + "controllers/controllerCart.php?option=" + option + "&value=" + value, true);
	xmlhttp.send();
}

function cancelOrder()
{
    var xmlhttp = getXMLHTTP();

	/* What to do when we get the asynchormous response from the server */
	/* What to do when we get the asynchormous response from the server */
	xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("viewCart").innerHTML = xmlhttp.responseText;
		}
	};

	/* Sending the asynchronous request to the server */
	xmlhttp.open("GET", getAbsoluteURL() + "controllers/controllerCart.php?option=3", true);
	xmlhttp.send();
}

function updateQuantity(quantity, idProductCart)
{
    var xmlhttp = getXMLHTTP();

    /* What to do when we get the asynchormous response from the server */
    /* What to do when we get the asynchormous response from the server */
    xmlhttp.onreadystatechange = function()
    {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
        {
            document.getElementById("updateQuantity").innerHTML = xmlhttp.responseText;
        }
    };

    /* Sending the asynchronous request to the server */
    xmlhttp.open("GET", getAbsoluteURL() + "controllers/controllerCart.php?option=4&quantity=" + quantity + "&idProductCart=" + idProductCart, true);
    xmlhttp.send();
}


// Funcion para mostrar un producto en específico
function loadProduct(productID)
{
    window.location.href="controllers/controllerProduct.php?productID="+productID;
}

function loadSearchProduct(productID)
{
    window.location.href="controllerProduct.php?productID="+productID;
}



// JQUERY

// MUNU DESPLEGABLE NAV PRINCIPAL
$(document).ready(function()
{
   // Muestra y oculta los menús
   $('.li-dropdown').hover(
      function(e)
      {
         $(this).find('.ul-dropdown-content').css({display: "block"});
      },
      function(e)
      {
         $(this).find('.ul-dropdown-content').css({display: "none"});
      }
   );
});
