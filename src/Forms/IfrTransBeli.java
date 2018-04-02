/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author user
 */
public class IfrTransBeli extends javax.swing.JInternalFrame {
private DefaultTableModel tbltransbeli;


Connection _Cnn;
Statement stat;
ResultSet res;


    /**
     * Creates new form IfrTransBeli
     */
    public IfrTransBeli() {
        initComponents();
        clearInput();
        siapIsi(false);
        tombolNormal();
        txtSubTotal.setVisible(false);
        Object header[]={"Kode Barang","Judul Buku","Harga Beli","Jumlah","SubTotal"};
        tbltransbeli=new DefaultTableModel(null, header);
    }
    
    public void clearInput(){
        txtNoTrans.setText("");
        txtSup.setText("");
        txtTgl.setDate(new Date());
        txtKdBuku.setText("");
        txtJudulBuku.setText("");
        txtHargaBeli.setText("");
        txtJumlah.setText("");
        txtSubTotal.setText("0");
        txtTotal.setText("0");
    }
    
    public void siapIsi(boolean a){
        txtNoTrans.setEnabled(a);
        txtSup.setEnabled(a);
        txtKdBuku.setEnabled(a);
        txtJudulBuku.setEnabled(a);
        txtHargaBeli.setEnabled(a);
        txtJumlah.setEnabled(a);
        txtTotal.setEnabled(a); 
    }
    
    private void tombolNormal(){
        btnTambah.setEnabled(true);
        btnSimpan2.setEnabled(false);
        btnSimpan1.setEnabled(false);
        btnHapus.setEnabled(false);
        btnSup.setEnabled(false);
        btnCariKdBuku.setEnabled(false);
    }
    
    private void tampilBuku(){
        Object header[]={"Kode Barang","Judul Buku","Pengarang","Penerbit","Harga Beli","Harga Jual","Stok"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tbDataBuku.setModel(data);
        setKoneksi();
        String sql="select * from tbbuku";
        
        try {
            ResultSet res=stat.executeQuery(sql);
            while (res.next())
            {
                String kolom1=res.getString(1);
                String kolom2=res.getString(2);
                String kolom3=res.getString(3);
                String kolom4=res.getString(4);
                String kolom5=res.getString(5);
                String kolom6=res.getString(6);
                String kolom7=res.getString(7);
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }        
    }
    
    private void tampilSupplier(){
        Object header[]={"ID Supplier","No.Fatktur Beli","Nama Supplier","Nama Perusahaan","Alamat","No. Telepon"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tbDataSupplier.setModel(data);
        setKoneksi();
        String sql="select*from tbsupplier";
        try {
            ResultSet res=stat.executeQuery(sql);
            while (res.next())
            {
                String kolom1=res.getString(1);
                String kolom2=res.getString(2);
                String kolom3=res.getString(3);
                String kolom4=res.getString(4);
                String kolom5=res.getString(5);
                String kolom6=res.getString(6);
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }
    
    private void ambilData(){
     try {
            tbPembelian.setModel(tbltransbeli);
                String kolom1 = txtKdBuku.getText();
                String kolom2 = txtJudulBuku.getText();
                String kolom3 = txtHargaBeli.getText();
                String kolom4 = txtJumlah.getText();
                String kolom5 = txtSubTotal.getText();
                String[] kolom = {kolom1, kolom2, kolom3, kolom4, kolom5};
                tbltransbeli.addRow(kolom);
          }
          catch (Exception ex) {
              JOptionPane.showMessageDialog(null, "Data gagal disimpan");
          }        
    }
    
    private void hapusdatadaritabel(){
        int a = tbPembelian.getSelectedRow();
        if(a == -1){
        }tbltransbeli.removeRow(a);
    }
    
    private void simpan(){
        setKoneksi();
        try {
            Date skrg=new Date();
            SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");
            String tgl=frm.format(skrg); 
            
            int t = tbPembelian.getRowCount();
            for(int i=0;i<t;i++)    
            {
            String kd_buku=tbPembelian.getValueAt(i, 0).toString();
            String judul_buku=tbPembelian.getValueAt(i, 1).toString();
            int harga_beli= Integer.parseInt(tbPembelian.getValueAt(i, 2).toString());
            int stok= Integer.parseInt(tbPembelian.getValueAt(i, 3).toString());
            int subtotal= Integer.parseInt(tbPembelian.getValueAt(i, 4).toString());
         
            String sql ="insert into tbpembelian values('"+txtNoTrans.getText()+"','"+tgl+"','"+txtSup.getText()+"','"
                    +kd_buku+"','"+harga_beli+"','"+stok+"','"+subtotal+"','"+txtTotal.getText()+"')";
            
            stat.executeUpdate(sql);
            }         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Simpan transaksi gagal");
        }
    }
    
    
    
    private void otomatis(){
        try {
            setKoneksi();
            String sql="select right (no_transbeli,2)+1 from tbpembelian";
            ResultSet res=stat.executeQuery(sql);
            if(res.next()){
                res.last();
                String no=res.getString(1);
                while (no.length()<3){
                    no="0"+no;
                    txtNoTrans.setText("P"+no);}
                }
            else
            {
                txtNoTrans.setText("P001"); 
        }
        } catch (Exception e) 
        {
        }
        
   
    }
    
    private void clearTabel(){
        int row  = tbltransbeli.getRowCount();
        for(int i=0; i<row; i++){
            tbltransbeli.removeRow(0);
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

        jDialog1 = new javax.swing.JDialog();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDataBuku = new javax.swing.JTable();
        txtCariBuku = new javax.swing.JTextField();
        btnCariBuku = new javax.swing.JButton();
        jDialog2 = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbDataSupplier = new javax.swing.JTable();
        txtCariSup = new javax.swing.JTextField();
        btnCariSup = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCariKdBuku = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnSimpan1 = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPembelian = new javax.swing.JTable();
        btnTambah = new javax.swing.JButton();
        btnSimpan2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtNoTrans = new javax.swing.JTextField();
        txtSup = new javax.swing.JTextField();
        btnSup = new javax.swing.JButton();
        txtSubTotal = new javax.swing.JTextField();
        txtKdBuku = new javax.swing.JTextField();
        txtJudulBuku = new javax.swing.JTextField();
        txtHargaBeli = new javax.swing.JTextField();
        txtJumlah = new javax.swing.JTextField();
        txtTgl = new com.toedter.calendar.JDateChooser();

        jDialog1.setMinimumSize(new java.awt.Dimension(460, 386));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("DATA BUKU");

        tbDataBuku.setModel(new javax.swing.table.DefaultTableModel(
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
        tbDataBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataBukuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDataBuku);

        txtCariBuku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariBukuKeyPressed(evt);
            }
        });

        btnCariBuku.setText("Cari");
        btnCariBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariBukuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addComponent(txtCariBuku)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCariBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(184, 184, 184))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariBuku))
                .addGap(29, 29, 29))
        );

        jDialog2.setMinimumSize(new java.awt.Dimension(460, 386));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("DATA SUPPLIER");

        tbDataSupplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tbDataSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSupplierMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbDataSupplier);

        txtCariSup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariSupKeyPressed(evt);
            }
        });

        btnCariSup.setText("Cari");
        btnCariSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariSupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addComponent(txtCariSup)
                .addGap(18, 18, 18)
                .addComponent(btnCariSup, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addGap(39, 39, 39)
                .addGroup(jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariSup))
                .addGap(5, 5, 5))
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setForeground(new java.awt.Color(51, 204, 255));

        jLabel1.setBackground(new java.awt.Color(51, 204, 255));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("     TRANSAKSI PEMBELIAN");
        jLabel1.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        jLabel3.setText("Supplier");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel11.setText("Tanggal");

        jLabel5.setText("Kode Buku");

        btnCariKdBuku.setText("...");
        btnCariKdBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariKdBukuActionPerformed(evt);
            }
        });

        jLabel6.setText("Judul Buku");

        jLabel7.setText("Harga Beli");

        jLabel9.setText("Jumlah");

        btnSimpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save-black.png"))); // NOI18N
        btnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan1ActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/btn_delete.png"))); // NOI18N
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        tbPembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Judul Buku", "Harga Beli", "Jumlah", "SubTotal"
            }
        ));
        jScrollPane1.setViewportView(tbPembelian);

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan2.setText("Simpan");
        btnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpan2ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Total");

        txtTotal.setForeground(new java.awt.Color(255, 0, 51));
        txtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalKeyTyped(evt);
            }
        });

        jLabel4.setText("No. Trans");

        txtNoTrans.setForeground(new java.awt.Color(51, 255, 51));
        txtNoTrans.setDisabledTextColor(new java.awt.Color(51, 255, 51));

        btnSup.setText("..");
        btnSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupActionPerformed(evt);
            }
        });

        txtHargaBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaBeliKeyTyped(evt);
            }
        });

        txtJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtJumlahKeyTyped(evt);
            }
        });

        txtTgl.setToolTipText("");
        txtTgl.setDateFormatString("dd MMMM yyyy");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                            .addComponent(txtKdBuku))
                                        .addGap(0, 0, 0)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnCariKdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel9)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txtJumlah)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btnSimpan1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtSup, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnSup, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(375, 375, 375)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtNoTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(74, 74, 74))
                                    .addComponent(txtTgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNoTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSup)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSimpan1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCariKdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtKdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(btnSimpan2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariKdBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariKdBukuActionPerformed
        jDialog1.setLocationRelativeTo(null);
        tampilBuku();
        jDialog1.setVisible(true);
        txtCariBuku.setText("");
       
    }//GEN-LAST:event_btnCariKdBukuActionPerformed

    private void btnSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupActionPerformed
        jDialog2.setLocationRelativeTo(null);
        tampilSupplier();
        jDialog2.setVisible(true);
        txtCariSup.setText("");
    }//GEN-LAST:event_btnSupActionPerformed

    private void tbDataBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataBukuMouseClicked
        int baris = tbDataBuku.getSelectedRow();
        txtKdBuku.setText(tbDataBuku.getModel().getValueAt(baris, 0).toString());
        txtJudulBuku.setText(tbDataBuku.getModel().getValueAt(baris, 1).toString());
        jDialog1.dispose();
    }//GEN-LAST:event_tbDataBukuMouseClicked

    private void txtCariBukuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariBukuKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Object header[]={"Kode Barang","Judul Buku","Pengarang","Penerbit","Harga Beli","Harga Jual","Stok"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tbDataBuku.setModel(data);
        setKoneksi();
        String sql="Select * from tbbuku where kd_buku like '%" + txtCariBuku.getText() + "%'" + "or judul_buku like '%" + txtCariBuku.getText()+ "%'";
        try {
            ResultSet res=stat.executeQuery(sql);
            while (res.next())
            {
                String kolom1=res.getString(1);
                String kolom2=res.getString(2);
                String kolom3=res.getString(3);
                String kolom4=res.getString(4);
                String kolom5=res.getString(5);
                String kolom6=res.getString(6);
                String kolom7=res.getString(7);
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
        }
    }//GEN-LAST:event_txtCariBukuKeyPressed

    private void txtCariSupKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariSupKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Object header[]={"ID Supplier","No.Fatktur Beli","Nama Supplier","Nama Perusahaan","Alamat","No. Telepon"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tbDataSupplier.setModel(data);
        setKoneksi();
        String sql="Select * from tbsupplier where id_supplier like '%" + txtCariSup.getText() + "%'" + "or nama_supplier like '%" + txtCariSup.getText()+ "%'";
        try {
            ResultSet res=stat.executeQuery(sql);
            while (res.next())
            {
                String kolom1=res.getString(1);
                String kolom2=res.getString(2);
                String kolom3=res.getString(3);
                String kolom4=res.getString(4);
                String kolom5=res.getString(5);
                String kolom6=res.getString(6);
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }}
    }//GEN-LAST:event_txtCariSupKeyPressed

    private void btnCariBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariBukuActionPerformed
        Object header[]={"Kode Barang","Judul Buku","Pengarang","Penerbit","Harga Beli","Harga Jual","Stok"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tbDataBuku.setModel(data);
        setKoneksi();
        String sql="Select * from tbbuku where kd_buku like '%" + txtCariBuku.getText() + "%'" + "or judul_buku like '%" + txtCariBuku.getText()+ "%'";
        try {
            ResultSet res=stat.executeQuery(sql);
            while (res.next())
            {
                String kolom1=res.getString(1);
                String kolom2=res.getString(2);
                String kolom3=res.getString(3);
                String kolom4=res.getString(4);
                String kolom5=res.getString(5);
                String kolom6=res.getString(6);
                String kolom7=res.getString(7);
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnCariBukuActionPerformed

    private void tbDataSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSupplierMouseClicked
        int baris = tbDataSupplier.getSelectedRow();
        txtSup.setText(tbDataSupplier.getModel().getValueAt(baris, 0).toString());
        jDialog2.dispose();
    }//GEN-LAST:event_tbDataSupplierMouseClicked

    private void btnCariSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariSupActionPerformed
        Object header[]={"ID Supplier","No.Fatktur Beli","Nama Supplier","Nama Perusahaan","Alamat","No. Telepon"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tbDataSupplier.setModel(data);
        setKoneksi();
        String sql="Select * from tbsupplier where id_supplier like '%" + txtCariSup.getText() + "%'" + "or nama_supplier like '%" + txtCariSup.getText()+ "%'";
        try {
            ResultSet res=stat.executeQuery(sql);
            while (res.next())
            {
                String kolom1=res.getString(1);
                String kolom2=res.getString(2);
                String kolom3=res.getString(3);
                String kolom4=res.getString(4);
                String kolom5=res.getString(5);
                String kolom6=res.getString(6);
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6};
                data.addRow(kolom);
            }
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnCariSupActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if(btnTambah.getText().equalsIgnoreCase("tambah")){
            btnTambah.setText("Batal");
            siapIsi(true);
            clearInput();
            otomatis();
            txtSup.requestFocus();
            txtNoTrans.setEnabled(false);
            btnSimpan2.setEnabled(true);
            btnSimpan1.setEnabled(true);
            btnHapus.setEnabled(true);
            btnCariKdBuku.setEnabled(true);
            btnSup.setEnabled(true);
        }else{
            clearInput();
            siapIsi(false);
            btnTambah.setText("Tambah");
            tbltransbeli.getDataVector().removeAllElements();
            tbltransbeli.fireTableDataChanged();
            tombolNormal();
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        int baris = tbPembelian.getSelectedRow();
        int qty=Integer.parseInt(tbPembelian.getModel().getValueAt(baris, 3).toString());
        int total=Integer.parseInt(txtTotal.getText());
        int harga=Integer.parseInt(tbPembelian.getModel().getValueAt(baris, 2).toString());
        
        int totbay=total-(harga*qty);
        txtTotal.setText(Integer.toString(totbay)); 
        hapusdatadaritabel();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan1ActionPerformed
        // TODO add your handling code here:
        int harga=Integer.parseInt(txtHargaBeli.getText());
        int jml=Integer.parseInt(txtJumlah.getText());
        int total=Integer.parseInt(txtTotal.getText());
        
            int subtotal=harga*jml;
            txtSubTotal.setText(Integer.toString(subtotal));
            
            int totbayar=total+(harga*jml);
            txtTotal.setText(Integer.toString(totbayar));
            ambilData();
            
           
        otomatis();
        txtKdBuku.setText("");
        txtJudulBuku.setText("");
        txtHargaBeli.setText("");
        txtJumlah.setText("");
        
    }//GEN-LAST:event_btnSimpan1ActionPerformed

    private void btnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpan2ActionPerformed
         if(txtNoTrans.getText().equals("") || txtSup.getText().equals("") ){
            JOptionPane.showMessageDialog(null, "Lengkapi inputan pembelian barang");
        } else{
            simpan();
            JOptionPane.showMessageDialog(null, "Simpan Transaksi Berhasil");
               
           
        }
           if(btnSimpan2.getText().equalsIgnoreCase("Simpan")){
            clearInput();
            siapIsi(false);
            btnTambah.setText("Tambah");
            tbltransbeli.getDataVector().removeAllElements();
            tbltransbeli.fireTableDataChanged();
            tombolNormal();
        }else{
            
        }
       
       
    }//GEN-LAST:event_btnSimpan2ActionPerformed

    private void txtHargaBeliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaBeliKeyTyped
        char karakter = evt.getKeyChar();
        if (!(Character.isDigit(karakter) || karakter==KeyEvent.VK_BACK_SPACE))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaBeliKeyTyped

    private void txtJumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if (!(Character.isDigit(karakter) || karakter==KeyEvent.VK_BACK_SPACE))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtJumlahKeyTyped

    private void txtTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if (!(Character.isDigit(karakter) || karakter==KeyEvent.VK_BACK_SPACE))
        {
            evt.consume();
        }
    }//GEN-LAST:event_txtTotalKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCariBuku;
    private javax.swing.JButton btnCariKdBuku;
    private javax.swing.JButton btnCariSup;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan1;
    private javax.swing.JButton btnSimpan2;
    private javax.swing.JButton btnSup;
    private javax.swing.JButton btnTambah;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbDataBuku;
    private javax.swing.JTable tbDataSupplier;
    private javax.swing.JTable tbPembelian;
    private javax.swing.JTextField txtCariBuku;
    private javax.swing.JTextField txtCariSup;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtJudulBuku;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKdBuku;
    private javax.swing.JTextField txtNoTrans;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtSup;
    private com.toedter.calendar.JDateChooser txtTgl;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables


public Connection setKoneksi(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            _Cnn=DriverManager.getConnection("jdbc:mysql://localhost/db_pos1","root","");
            stat=_Cnn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Koneksi Gagal :" +e);
        }
       return _Cnn; 
    }
}
