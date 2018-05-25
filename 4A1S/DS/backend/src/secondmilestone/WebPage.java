package secondmilestone;

/*
 * Imported necessary libraries.
 */
import java.util.Collection;
import java.util.Iterator;
import html.Attribute;
import html.Tag;

/**
 * Web Page.
 *
 * @author Ramon 1400214, David 1391968
 *
 */
public class WebPage {

    /**
     * HTML tag.
     */
    private Tag webPage = new Tag("html");

    /**
     * HTML head tag.
     */
    private Tag head = new Tag("head");

    /**
     * HTML body tag.
     */
    private Tag body = new Tag("body");

    /**
     * Number one, 1.
     */
    private static final int ONE = 1;

    /**
     * Number six, 6.
     */
    private static final int SIX = 6;

    /**
     * Constructor.
     */
    public WebPage() {
        Tag title = new Tag("title");
        title.add("Informe TimeTracker");
        head.add(title);
        webPage.add(head);
        webPage.add(body);
    }

    /**
     * Adds header to the web page.
     *
     * @param str value of header
     * @param size h1 to h6
     * @param center align:center, true if aligned
     */
    public void addHeader(
            final String str,
            final int size,
            final boolean center) {
        if (size >= ONE && size <= SIX) {
            Tag h = new Tag("h" + (new Integer(size)).toString());
            h.add(str);
            if (center) {
                h.addAttribute(new Attribute("style", "text-align: center;"));
            }
            body.add(h);
        }
    }

    /**
     * Adds text to the body.
     *
     * @param str text to add
     */
    public void addNormalText(final String str) {
        body.add(str);
    }

    /**
     * Adds line break to the body.
     */
    public void addLineBreak() {
        body.add(new Tag("br", false));
    }

    /**
     * Adds a table to the body.
     *
     * @param table table to add
     * @param primeraFilaCapsalera primera fila de header
     * @param primeraColumnaCapsalera primera columna de header
     */
	public void afegeixTaula(
			final Collection table,
			final boolean primeraFilaCapsalera,
			final boolean primeraColumnaCapsalera) {
		// taula es una llista (files) de llistes (columnes), implementat com un
		// arraylist d'arraylists, encara que aqui per mes generalitat hi posem
		// el tipus generic collection

		/*
		 * Exemple : taula amb capsalera a la primera fila
		 *
		 * <table style= "text-align: left; width: 842px;" border="1" cellpadding="2" cellspacing="2">
		 * 		<tbody>
		 * 			<tr>
		 * 				<th style="background-color: rgb(102, 255, 255);">No.</th>
		 * 				<th style="background-color: rgb(102, 255, 255);">Projecte</th>
		 * 				<th style="background-color: rgb(102, 255, 255);">Data d'inici</th>
		 * 				<th style="background-color: rgb(102, 255, 255);">Data final</th>
		 * 				<th style="background-color: rgb(102, 255, 255);">Temps total</th>
		 * 			</tr>
		 * 			<tr>
		 * 				<td style="background-color: rgb(204, 255, 255);">1</td>
		 * 				<td style="background-color: rgb(204, 255, 255);">P&agrave;gina web personal</td>
		 * 				<td style="background-color: rgb(204, 255, 255);">15/11/2006, 19:00h</td>
		 * 				<td style="background-color: rgb(204, 255, 255);">25/11/2006, 20:00h</td>
		 * 				<td style="background-color: rgb(204, 255, 255);">25h 45m 0s</td>
		 * 			</tr>
		 * 		</tbody>
		 * </table>
		 */
		Tag t = new Tag("table");
		t.addAttribute(new Attribute(
		        "style",
		        "text-align: left; width: 842px;"));
		t.addAttribute(new Attribute("border", "1"));
		t.addAttribute(new Attribute("cellpadding", "2"));
		t.addAttribute(new Attribute("cellspacing", "2"));

		Tag tbody = new Tag("tbody");
		// les cel.les de capsalera tenen fons en blau fosc
		Attribute estilTh = new Attribute(
		        "style",
		        "background-color: rgb(102, 255, 255);");
		// les cel.les de dades, fons en blau clar
		Attribute estilTd = new Attribute(
		        "style",
		        "background-color: rgb(204, 255, 255);");

		Iterator itFiles    = table.iterator();
		Iterator itColumnes = null;
		boolean primeraFila = true;

		while (itFiles.hasNext()) {
			Tag tr = new Tag("tr"); // cada fila de la taula
			itColumnes = ((Collection) itFiles.next()).iterator();
			boolean primeraColumna = true;

			while (itColumnes.hasNext()) {
				if ((primeraFila && primeraFilaCapsalera)
				    ||
				   (primeraColumna && primeraColumnaCapsalera)
				   ) {
				    // th en comptes de td

					Tag th = new Tag("th");
					th.addAttribute(estilTh);
					th.add(itColumnes.next().toString());
					tr.add(th);

				} else {
					Tag td = new Tag("td");
					td.addAttribute(estilTd);
					td.add(itColumnes.next().toString());
					tr.add(td);
				}
				primeraColumna = false;
			}
			primeraFila = false;
			tbody.add(tr);
		}
		t.add(tbody);
		body.add(t);
	}

	/**
	 * Adds a thematic break in HTML.
	 */
	public void addThematicBreak() {
		Tag hr = new Tag("hr");
		hr.addAttribute(
		        new Attribute(
		                "style",
		                "width: 100%; height: 2px;")
		        );
		body.add(hr);
	}

	/**
	 * Prints the web page in console.
	 */
	public void printPage() {
		System.out.println(this.webPage);
	}

	/**
	 * Gets the web page created as a string.
	 *
	 * @return HTML code as string
	 */
	public String getText() {
		return this.webPage.toString();
	}
}
