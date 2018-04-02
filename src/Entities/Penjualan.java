/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;
import Tools.KoneksiDB;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author user
 */
public class Penjualan {
    public int no_trans_jual;
    public int total;
    
    public Connection _Cnn;
    public KoneksiDB getCnn = new KoneksiDB();
    public String query;
    public boolean isUpdate;
    public PreparedStatement stat;
    public ResultSet res;
    public DefaultTableModel tblthangkatan = new DefaultTableModel();
    public List<Object> list;
    
    
    
    
    //membuat ID. Tahun Angkatan otomatis
    public void createAutoID(){
        try {
           _Cnn = getCnn.getConnection();
           query = "select max(no_trans_jual) from tbpenjualan";
           stat = _Cnn.prepareStatement(query);
           res = stat.executeQuery(query);
           if(res.first()){
               no_trans_jual = res.getInt(1)+1;
           }else{
               no_trans_jual = 1;
           }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error method createAutoID() : "
            + ex, "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
