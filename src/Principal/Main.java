
package Principal;

import GUI.JFPrincipal;


public class Main {

    
    //se manda a mostrar JF Principal/
    public static void main(String[] args) {
        JFPrincipal frmprincipal = new JFPrincipal();
		frmprincipal.setExtendedState(frmprincipal.MAXIMIZED_BOTH);
		frmprincipal.show();
    }
    
}
