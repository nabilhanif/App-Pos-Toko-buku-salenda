package Forms;
//import file class library yang dibutuhkan :

import Tools.KoneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ica
 */

public class IfrSupplier extends javax.swing.JInternalFrame {
    

    KoneksiDB getCnn = new KoneksiDB(); // koneksi database
    Connection _Cnn; // class connection
    
    String sqlselect, sqlinsert, sqldelete;
    String vid_supplier, vnama_perusahaan, vnama_supplier,vno_faktur ,valamat, vno_tlp,vstatus;
    private DefaultTableModel tblsupplier;
    
    public IfrSupplier() {
        initComponents();
            clearInput();
            setTabelSupplier();
            showDataSupplier();
            jdInputSupplier.setSize(580, 380);
            jdInputSupplier.setLocationRelativeTo(this);
    }
    
    private void clearInput(){
        txtIdsupplier.setText("");
        txtNamaSupplier.setText("");
        txtNamaPerusahaan.setText("");
        txtNoFaktur.setText("");
        txtAlamat.setText("");
        txtNoTelepon.setText("");
        txtStatus.setText("");
        btnSimpan.setText("Simpan");
        vid_supplier="";
    }
    
    private void setTabelSupplier(){
        
         String[] kolom1 = {"ID Supplierz", "Nama Supplier", "Nama Perusahaan ", 
             "No Faktur ","Alamat", "No. Telepon","Status"};
        tblsupplier = new DefaultTableModel(null, kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                
            };
            public Class getColumnClass (int columnIndex){
                return types [columnIndex];
            }
            
            // agar tabel tidak bisa diedit
            public boolean isCellEdittable(int row, int col){
                int cola = tblsupplier.getColumnCount();
                return (col < cola ) ? false : true;
            }
        }; 
        tbDataSupplier.setModel(tblsupplier);
        tbDataSupplier.getColumnModel().getColumn(0).setPreferredWidth(75);
        tbDataSupplier.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataSupplier.getColumnModel().getColumn(2).setPreferredWidth(75);
        tbDataSupplier.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataSupplier.getColumnModel().getColumn(4).setPreferredWidth(75);
        tbDataSupplier.getColumnModel().getColumn(5).setPreferredWidth(75);
        tbDataSupplier.getColumnModel().getColumn(5).setPreferredWidth(75);
       

    }
    
    private void ClearTabelSupplier(){
         int row = tblsupplier.getRowCount();
        for(int i = 0 ; i < row ; i++){
            tblsupplier.removeRow(0);
        } 
    }
    
    private void showDataSupplier(){
        try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            ClearTabelSupplier();
           sqlselect = "select * from tbsupplier "
                    + "where id_supplier ='"+vid_supplier+"' ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_supplier = res.getString("id_supplier");
                vnama_supplier = res.getString("nama_supplier");
                vnama_perusahaan = res.getString("nama_perusahaan");
                vno_faktur = res.getString("no_fakturbeli");
                valamat = res.getString("alamat_perusahaan");
                vno_tlp = res.getString("no_hp");
                vstatus = res.getString("status");
                Object[]data = {vid_supplier ,vnama_supplier, vnama_perusahaan,
                    vno_faktur, valamat, vno_tlp,vstatus};
                tblsupplier.addRow(data);
            }
            lbRecord.setText("Record : "+tbDataSupplier.getRowCount());
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error method showDataSupplier() :"+ex);
        } 
    }
    
    private void cariSupplier(){
        
        try {
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            ClearTabelSupplier();
            sqlselect = "select * from tbsupplier "
                    + "where id_Supplier ='"+vid_supplier+"' ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vid_supplier = res.getString("id_supplier");
                vnama_supplier = res.getString("nama_supplier");
                vnama_perusahaan = res.getString("nama_perusahaan");
                vno_faktur = res.getString("no_fakturbeli");
                valamat = res.getString("alamat_perusahaan");
                vno_tlp = res.getString("no_hp");
                vstatus = res.getString("status");
               
            }
            lbRecord.setText("Record : "+tbDataSupplier.getRowCount());
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error method showDataSupplier() :"+ex);
        }
    }
    
    
    
    private void getDatalSupplier(){
      try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            sqlselect = "select * from tbsupplier "
                    + "where id_supplier ='"+vid_supplier+"' ";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            if(res.first()){
                vid_supplier = res.getString("id_supplier");
                vnama_supplier = res.getString("nama_supplier");
                vnama_perusahaan = res.getString("nama_perusahaan");
                vno_faktur = res.getString("no_fakturbeli");
                valamat = res.getString("alamat_perusahaan");
                vno_tlp = res.getString("no_hp"); 
                vstatus = res.getString("status");
            }
           
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method getDataSupplier() : "+ex,
                "Data Berasil Disimpan !", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void aksiSimpan(){
       if(txtIdsupplier.getText().equals("")){
            JOptionPane.showMessageDialog(this, "informasi",
                    "Mohon lengkapi data!", JOptionPane.INFORMATION_MESSAGE);
       }else if(txtNamaSupplier.getText().equals("")){
            JOptionPane.showMessageDialog(this, "informasi",
                    "Mohon lengkapi data!", JOptionPane.INFORMATION_MESSAGE);
       }else if(txtNamaPerusahaan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "informasi",
                    "Mohon lengkapi data!", JOptionPane.INFORMATION_MESSAGE);
       }else if(txtNoFaktur.getText().equals("")){
            JOptionPane.showMessageDialog(this, "informasi",
                    "Mohon lengkapi data!", JOptionPane.INFORMATION_MESSAGE);
       }else if(txtAlamat.getText().equals("")){
            JOptionPane.showMessageDialog(this, "informasi",
                    "Mohon lengkapi data!", JOptionPane.INFORMATION_MESSAGE);
       }else if(txtNoTelepon.getText().equals("")){
            JOptionPane.showMessageDialog(this, "informasi",
                    "Mohon lengkapi data!", JOptionPane.INFORMATION_MESSAGE);
       }else if(txtStatus.getText().equals("")){
            JOptionPane.showMessageDialog(this, "informasi",
                    "Mohon lengkapi data!", JOptionPane.INFORMATION_MESSAGE);
       }else{
            vid_supplier = txtIdsupplier.getText();
            vnama_supplier = txtNamaSupplier.getText();
            vnama_perusahaan = txtNamaPerusahaan.getText();
            vno_faktur = txtNoFaktur.getText();
            valamat = txtAlamat.getText();
            vno_tlp = txtNoTelepon.getText();
            vstatus = txtStatus.getText();
           
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                if(btnSimpan.getText().equals("Simpan")){
                    sqlinsert = "insert into tbsupplier values('"+vid_supplier+"','"+vnama_supplier+"','"+vnama_perusahaan+"','"+vno_faktur+"','"+valamat+"','"+vno_tlp+"','"+vstatus+"')";
                }else if(btnSimpan.getText().equals("Ubah")){
                    sqlinsert = "update tbsupplier set"
                            + " nama_supplier='"+vnama_supplier+"', "
                            + " nama_perusaan='"+vnama_perusahaan+"', "
                            + " no_fakturbeli='"+vno_faktur+"', "
                            + " alamat_perusahaan='"+valamat+"', "
                            + " no_hp='"+vno_tlp+"', "
                            + " status='"+vstatus+"', "
                            + " where id_supplier='"+vid_supplier+"' ";          
                }
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqlinsert);
                JOptionPane.showMessageDialog(this, "Data Berhasil disimpan !",
                        "Data berhasil disimpan", JOptionPane.INFORMATION_MESSAGE);
                showDataSupplier();clearInput(); 
                
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksiSimpan() : "+ex);
            }
        }
   
    }

   
        
      
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdInputSupplier = new javax.swing.JDialog();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnBatal = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtIdsupplier = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNamaSupplier = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtAlamat = new javax.swing.JTextField();
        txtNoTelepon = new javax.swing.JTextField();
        txtNamaPerusahaan = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtNoFaktur = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnCetak = new javax.swing.JButton();
        lbRecord = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataSupplier = new javax.swing.JTable();

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Form Supplier");

        jLabel5.setText("Form ini digunakan untuk mengolah data supplier");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel2.setOpaque(false);

        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/batalsmall.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(181, 181, 181)
                .addComponent(btnSimpan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "From Data Supplier"));
        jPanel6.setOpaque(false);

        jLabel13.setText("ID Supplier                  : ");

        txtIdsupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdsupplierActionPerformed(evt);
            }
        });

        txtNamaSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaSupplierActionPerformed(evt);
            }
        });

        jLabel14.setText("No Telepon                :");

        jLabel12.setText("Alamat Perusahaan   :");

        jLabel9.setText("Nama Perusahaan     :");

        txtAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatActionPerformed(evt);
            }
        });

        txtNamaPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaPerusahaanActionPerformed(evt);
            }
        });

        jLabel16.setText("Nama Supplier            :");

        jLabel10.setText("No Faktur                   :");

        txtNoFaktur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoFakturActionPerformed(evt);
            }
        });

        jLabel11.setText("Status                        :");

        txtStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdsupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAlamat, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtNamaPerusahaan))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtNoFaktur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(txtIdsupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel8))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNamaSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtNamaPerusahaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jdInputSupplierLayout = new javax.swing.GroupLayout(jdInputSupplier.getContentPane());
        jdInputSupplier.getContentPane().setLayout(jdInputSupplierLayout);
        jdInputSupplierLayout.setHorizontalGroup(
            jdInputSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdInputSupplierLayout.createSequentialGroup()
                .addGroup(jdInputSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jdInputSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jdInputSupplierLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jdInputSupplierLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jdInputSupplierLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jdInputSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jdInputSupplierLayout.setVerticalGroup(
            jdInputSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jdInputSupplierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jdInputSupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jdInputSupplierLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setClosable(true);
        setTitle("Tabel Supplier");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel1.setOpaque(false);

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/insert.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Cari Supplier");

        btnCetak.setText("Cetak");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 14, Short.MAX_VALUE))
        );

        lbRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbRecord.setText("Recor :0");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tabel Data Buku : klik 2x untuk mengubah buku"));

        tbDataSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Supplier", "Nama Supplier", "Nama Perusahaan", "Alamat Perusahaan", "No Faktur Jual", "No Telepon", "status"
            }
        ));
        tbDataSupplier.setRowHeight(25);
        jScrollPane1.setViewportView(tbDataSupplier);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 817, Short.MAX_VALUE)
                        .addComponent(lbRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        clearInput();
        jdInputSupplier.setVisible(true);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
         cariSupplier();
    }//GEN-LAST:event_txtCariActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
          clearInput();
        
    }//GEN-LAST:event_btnBatalActionPerformed

    private void txtIdsupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdsupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdsupplierActionPerformed

    private void txtNamaSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaSupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaSupplierActionPerformed

    private void txtAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (txtIdsupplier.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Data NIM harus diisi", "Informasi",
                JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNamaSupplier.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Data nama supplier harus diisi", "Informasi",
                JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
            jdInputSupplier.setVisible(false);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtNoFakturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoFakturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoFakturActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCetakActionPerformed

    private void txtNamaPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaPerusahaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaPerusahaanActionPerformed

    private void txtStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JDialog jdInputSupplier;
    private javax.swing.JLabel lbRecord;
    private javax.swing.JTable tbDataSupplier;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtIdsupplier;
    private javax.swing.JTextField txtNamaPerusahaan;
    private javax.swing.JTextField txtNamaSupplier;
    private javax.swing.JTextField txtNoFaktur;
    private javax.swing.JTextField txtNoTelepon;
    private javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables
}
