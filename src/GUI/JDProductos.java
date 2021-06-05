
package GUI;

import Conexion_BD.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JDProductos extends javax.swing.JDialog {

    /**
     * Creates new form Productos
     */
    public JDProductos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Conexion basedatos = new Conexion();
        Connection conn;
        conn= basedatos.ObtenerConexion();
    }

    public void guardar(){
       int cod;
       String idp,nombrep,descripcionp,fechaa, fechav,precio;
       idp= idproducto.getText();
       nombrep= nombreproducto.getText();
       descripcionp= descripcion.getText();
       fechaa=new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser1.getDate());
       fechav=new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser2.getDate());
       precio=precioproducto.getText();
       
       if(idp.equals("")|| nombrep.equals("")||descripcionp.equals("")||fechaa.equals("")|| fechav.equals("")||
               precio.equals("")){
           System.out.println("Porfavor ingrese todos los "
                   + "datos de los camos");
       }
       else {
           try{
               Conexion basedatos= new Conexion();
               Connection conn;
               conn= basedatos.ObtenerConexion();
               PreparedStatement ps= null;
               ResultSet consulta7 = null;
               
               String sql="insert productos (idproducto, nombre, descripcion, fechaad, fechave, precio, estado) values (?,?,?,?,?,?,1)";
               
               ps=conn.prepareStatement(sql);
               String id= idproducto.getText();
               ps.setString(1, id);
               String nomb= nombreproducto.getText();
               ps.setString(2, nomb);
               String des= descripcion.getText();
               ps.setString(3, des);
               String fecha1 = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser1.getDate());
               ps.setString(4, fecha1);
               String fecha2 = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser2.getDate());
               ps.setString(5, fecha2);
               String pre= precioproducto.getText();
               ps.setString(6, pre);
               
               ps.execute();

               JOptionPane.showMessageDialog(this, "Los datos han sido almacenados de forma correcta");
               mostrar();
               Limpiar();
           }catch(Exception e){
               JOptionPane.showMessageDialog(this, "Error al almacenar la informacion");
           }
       }
   }
    
    public void mostrar(){
       Conexion basedatos= new Conexion();
               Connection conn;
               conn= basedatos.ObtenerConexion();
               PreparedStatement ps= null;
               ResultSet consulta = null;

               try{
                   Statement comando = conn.createStatement();
                   consulta= comando.executeQuery("select idproducto, nombre, descripcion, fechaad, fechave, precio from productos where estado !=0;");
                   DefaultTableModel modelo= new DefaultTableModel();
                   this.tbldatos.setModel(modelo);
                   ResultSetMetaData rmd = consulta.getMetaData();

                   int numcol = rmd.getColumnCount();

                   for(int i=0; i<numcol;i++){
                       modelo.addColumn(rmd.getColumnLabel(i+1));
                       }
                  
                        while(consulta.next()){
                       Object [] fila= new Object[numcol];
                       
                               
                       for(int i=0; i<numcol;i++){
                       fila[i]= consulta.getObject(i+1);
                       
                       for(int a=0; a<numcol;a++){
                            
                            fila[a]= consulta.getObject(a+1);
                       }
                       }
                       modelo.addRow(fila);
                       
                       }      

                   consulta.first();
                   
               }catch(Exception e){
                   System.out.println("Error"+e);
               }
               
   }

    public void Eliminar (){
    int filInicio= tbldatos.getSelectedRow();
    int numfila= tbldatos.getSelectedRowCount();
    
    ArrayList<String>listapersona= new ArrayList<>();
    String cod= null;
    
    if(filInicio>=0){
        for(int i=0; i<numfila; i++){
            cod= String.valueOf(tbldatos.getValueAt(i+filInicio,0));
            listapersona.add(i,cod);
        }
        for (int j=0; j<numfila;j++){
            int resp = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar el registro:  "+listapersona.get(j)+"? ");
            if(resp==0){
                int filAfectada= 0;
                
                try{
                     Conexion basedatos= new Conexion();
                     Connection conn;
                     conn= basedatos.ObtenerConexion();
                     PreparedStatement ps= null;
                     ResultSet consulta = null;
                     ps= conn.prepareStatement("UPDATE productos SET estado= 0 WHERE idproducto="+cod);
                     
                     int res= ps.executeUpdate();

                     if(res>0){
                         JOptionPane.showMessageDialog(null, "Los datos han sido Eliminados");
                         mostrar();
                     }else{
                         JOptionPane.showMessageDialog(null, "Error al Eliminar los datos");
                     }
                     
                }catch(Exception e){
                    System.err.println(e);
                }
            }
        }
    }else{
        JOptionPane.showMessageDialog(null, "Porfavor elija un registro para eliminar");
    }
}
    
    public void Modificar (){
    int filInicio= tbldatos.getSelectedRow();
    int numfila= tbldatos.getSelectedRowCount();
    
    ArrayList<String>listapersona= new ArrayList<>();
    String cod= null;
    
    if(filInicio>=0){
        for(int i=0; i<numfila; i++){
            cod= String.valueOf(tbldatos.getValueAt(i+filInicio,0));
            listapersona.add(i,cod);
        }
        for (int j=0; j<numfila;j++){
            int resp = JOptionPane.showConfirmDialog(null, "Esta seguro que desea actualizar el registro:  "+listapersona.get(j)+"? ");
            if(resp==0){
                int filAfectada= 0;
                 try{
                     desbloquear();
                     Conexion basedatos= new Conexion();
                     Connection conn;
                     conn= basedatos.ObtenerConexion();
                    PreparedStatement ps= null;
                     ResultSet consulta = null;
                     
                     ps= conn.prepareStatement("UPDATE productos SET idproducto=?, nombre=?, descripcion=?, fechaad=?, fechave=?, precio=? WHERE idproducto="+cod);
                     ps.setString(1, idproducto.getText());
                     ps.setString(2, nombreproducto.getText());
                     ps.setString(3, descripcion.getText());
                     String fecha = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser1.getDate());
                     ps.setString(4, fecha);
                     String fecha2 = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser2.getDate());
                     ps.setString(5, fecha2);
                     ps.setString(6, precioproducto.getText());
                    
                     int res= ps.executeUpdate();
                     if(res>0){
                         JOptionPane.showMessageDialog(null, "Los datos han sido Actualizados");
                         mostrar();
                         Limpiar();
                     }else{
                         JOptionPane.showMessageDialog(null, "Error al Actualizar los datos");
                     }
                     conn.close();
                }catch(Exception e){
                    System.err.println(e);
                }
                
            }
        }
    }else{
        JOptionPane.showMessageDialog(null, "Porfavor elija un registro para Actualizar");
    }
}
    public void Limpiar(){
        idproducto.setText("");
        nombreproducto.setText("");
        descripcion.setText("");
        jDateChooser1.setDateFormatString("");
        jDateChooser2.setDateFormatString("");
        precioproducto.setText("");
        
    }

    public void cargar(){
    if(tbldatos.getSelectedRowCount()>0){
        idproducto.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 0).toString());
        nombreproducto.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 1).toString());
        descripcion.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 2).toString());
        precioproducto.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 5).toString());        
        btnmostrar.setEnabled(false);
                btnmodificar.setEnabled(false);
                btneliminar.setEnabled(false);
                btnlimpiar.setEnabled(false);
                btnsalir.setEnabled(false);
                btncrear.setEnabled(false);
                btnaceptar.setEnabled(true);
    }
}
    void desbloquear(){
    btncrear.setEnabled(true);
    btnmodificar.setEnabled(true);
    btneliminar.setEnabled(true);
    btnaceptar.setEnabled(true);
    btnmostrar.setEnabled(true);
    btnlimpiar.setEnabled(true);
    btnsalir.setEnabled(true);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        idproducto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        nombreproducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        descripcion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        precioproducto = new javax.swing.JTextField();
        btncrear = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btnaceptar = new javax.swing.JButton();
        btnmostrar = new javax.swing.JButton();
        btnlimpiar = new javax.swing.JButton();
        btnsalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldatos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 36)); // NOI18N
        jLabel1.setText("Detalles de Producto");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("IDProducto:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nombre Producto:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Descipcion:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Fecha adquisición:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Fecha Vencimiento:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Precio del producto:");

        btncrear.setText("Crear");
        btncrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearActionPerformed(evt);
            }
        });

        btneliminar.setText("Eliminar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btnmodificar.setText("Modificar");
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });

        btnaceptar.setText("Aceptar");
        btnaceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaceptarActionPerformed(evt);
            }
        });

        btnmostrar.setText("Mostrar");
        btnmostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmostrarActionPerformed(evt);
            }
        });

        btnlimpiar.setText("Limpiar");
        btnlimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlimpiarActionPerformed(evt);
            }
        });

        btnsalir.setText("Salir");
        btnsalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btncrear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btneliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmodificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnaceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnmostrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnlimpiar))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(93, 93, 93)
                                .addComponent(idproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nombreproducto, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                                    .addComponent(descripcion)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(precioproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(121, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnsalir))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(idproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nombreproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(descripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(precioproducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncrear)
                    .addComponent(btneliminar)
                    .addComponent(btnmodificar)
                    .addComponent(btnaceptar)
                    .addComponent(btnmostrar)
                    .addComponent(btnlimpiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnsalir)
                .addContainerGap())
        );

        tbldatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbldatos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearActionPerformed
        guardar();
    }//GEN-LAST:event_btncrearActionPerformed

    private void btnmostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmostrarActionPerformed
        mostrar();
    }//GEN-LAST:event_btnmostrarActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        Eliminar();
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnlimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlimpiarActionPerformed
        Limpiar();
    }//GEN-LAST:event_btnlimpiarActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
        cargar();
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void btnaceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaceptarActionPerformed
       Modificar();
    }//GEN-LAST:event_btnaceptarActionPerformed

    private void btnsalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsalirActionPerformed
       System.exit(0);
    }//GEN-LAST:event_btnsalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDProductos dialog = new JDProductos(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnaceptar;
    private javax.swing.JButton btncrear;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnlimpiar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton btnmostrar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JTextField descripcion;
    private javax.swing.JTextField idproducto;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nombreproducto;
    private javax.swing.JTextField precioproducto;
    private javax.swing.JTable tbldatos;
    // End of variables declaration//GEN-END:variables
}
