
package GUI;
import Conexion_BD.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class JDVentas extends javax.swing.JDialog {

  
    public JDVentas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Conexion basedatos = new Conexion();
        Connection conn;
        conn= basedatos.ObtenerConexion();
    }

   public void guardar(){
       int cod;
       String idv,nombrepr,precioun,cantidad1,nit1,ncliente;
       idv= idventa.getText();
       nombrepr= nombrep.getText();
       precioun= preciou.getText();
       cantidad1=cantidad.getText();
       nit1=nit.getText();
       ncliente=nombrecliente.getText();
       
       if(idv.equals("")|| nombrep.equals("")||nombrepr.equals("")||precioun.equals("")|| nit1.equals("")||
               ncliente.equals("")){
           System.out.println("Porfavor ingrese todos los "
                   + "datos de los camos");
       }
       else {
           try{
               Conexion basedatos= new Conexion();
               Connection conn;
               conn= basedatos.ObtenerConexion();
               PreparedStatement ps1= null;
               ResultSet consulta = null;
               
               String sql1="insert ventas (idventa, nombre_producto, precio_unitario, cantidad, precio_total,"
                       + " nit_cliente,nombre_cliente, descuento, estado) values (?,?,?,?,?,?,?,?,1)";
               
               ps1=conn.prepareStatement(sql1);
               String id= idventa.getText();
               ps1.setString(1, id);
               String nomb= nombrep.getText();
               ps1.setString(2, nomb);
               String prec= preciou.getText();
               ps1.setString(3, prec);
               String cant= cantidad.getText();
               ps1.setString(4, cant);
               String pret= preciot.getText();
               ps1.setString(5, pret);
               String nitc= nit.getText();
               ps1.setString(6, nitc);
               String nombc= nombrecliente.getText();
               ps1.setString(7, nombc);
               String descuento1= desc.getText();
               ps1.setString(8, descuento1);

               ps1.execute();

               JOptionPane.showMessageDialog(this, "Los datos han sido almacenados de forma correcta");
               
           }catch(Exception e){
               JOptionPane.showMessageDialog(this, "Error al almacenar la informacion");
           }
       }
   }
   
   public void operaciones(){
       String total= cantidad.getText();
       String total2= preciou.getText();
           Integer proceso1= Integer.valueOf (total);
           Integer proceso2= Integer.valueOf (total2);
           Integer resultado=proceso1*proceso2;
           preciot.setText(String.valueOf(resultado));
               
           if(resultado>500){
               double resultado2=resultado*0.10;
               desc.setText(String.valueOf(resultado2));
           }else{
               desc.setText("0");
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
                   consulta= comando.executeQuery("select idventa,nombre_producto, precio_unitario, cantidad, precio_total,"
                       + " nit_cliente,nombre_cliente,descuento from ventas where estado !=0;");
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
                     ps= conn.prepareStatement("UPDATE ventas SET estado= 0 WHERE idventa="+cod);
                     
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
                     
                     ps= conn.prepareStatement("UPDATE ventas SET idventa=?, nombre_producto=?, precio_unitario=?, cantidad=?,"
                             + " precio_total=?, nit_cliente=?, nombre_cliente=?,descuento=? WHERE idventa="+cod);
                     ps.setString(1, idventa.getText());
                     ps.setString(2, nombrep.getText());
                     ps.setString(3, preciou.getText());
                     ps.setString(4, cantidad.getText());
                     ps.setString(5, preciot.getText());
                     ps.setString(6, nit.getText());
                     ps.setString(7, nombrecliente.getText());
                     ps.setString(8, desc.getText());
                    
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
   
    public void cargar(){
    if(tbldatos.getSelectedRowCount()>0){
        idventa.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 0).toString());
        nombrep.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 1).toString());
        preciou.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 2).toString());
        cantidad.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 3).toString());   
        preciot.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 4).toString());  
        nit.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 5).toString());  
        nombrecliente.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 6).toString());  
        desc.setText(tbldatos.getValueAt(tbldatos.getSelectedRow(), 7).toString());  
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
    
    public void Limpiar(){
        idventa.setText("");
        nombrep.setText("");
        preciou.setText("");
        cantidad.setText("");
        nit.setText("");
        nombrecliente.setText("");
        preciot.setText("");
        desc.setText("");
        
        
        
        
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        idventa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        nombrep = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        preciou = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        preciot = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        desc = new javax.swing.JTextField();
        btncalcular = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        nit = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cantidad = new javax.swing.JTextField();
        nombrecliente = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("VENTAS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Id Venta:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Nombre Producto:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Precio Unitario:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Precio Total:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Descuento");

        btncalcular.setText("Calcular");
        btncalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncalcularActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("NIT Cliente:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Nombre Cliente");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Cantidad:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(nombrep, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(idventa))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(desc)
                            .addComponent(preciot)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(nombrecliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(cantidad, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nit, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(preciou, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btncalcular)
                        .addGap(95, 95, 95)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(idventa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(preciot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nombrep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(desc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(preciou, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncalcular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(nit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(nombrecliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btncrear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btneliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnmodificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnaceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnmostrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnlimpiar)
                .addGap(18, 18, 18)
                .addComponent(btnsalir)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btncrear)
                    .addComponent(btneliminar)
                    .addComponent(btnmodificar)
                    .addComponent(btnaceptar)
                    .addComponent(btnmostrar)
                    .addComponent(btnlimpiar)
                    .addComponent(btnsalir))
                .addContainerGap(21, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(179, 179, 179)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 33, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearActionPerformed
        guardar();
    }//GEN-LAST:event_btncrearActionPerformed

    private void btncalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncalcularActionPerformed
        operaciones();
    }//GEN-LAST:event_btncalcularActionPerformed

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
            java.util.logging.Logger.getLogger(JDVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDVentas dialog = new JDVentas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btncalcular;
    private javax.swing.JButton btncrear;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btnlimpiar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton btnmostrar;
    private javax.swing.JButton btnsalir;
    private javax.swing.JTextField cantidad;
    private javax.swing.JTextField desc;
    private javax.swing.JTextField idventa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nit;
    private javax.swing.JTextField nombrecliente;
    private javax.swing.JTextField nombrep;
    private javax.swing.JTextField preciot;
    private javax.swing.JTextField preciou;
    private javax.swing.JTable tbldatos;
    // End of variables declaration//GEN-END:variables
}
