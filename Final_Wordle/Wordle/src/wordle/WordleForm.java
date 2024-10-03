/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordle;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;


public class WordleForm extends javax.swing.JFrame implements KeyListener, ActionListener{
static ArrayList<String> dict = new ArrayList<>();
static Random r = new Random();
static ArrayList<JLabel> Jcolumns = new ArrayList();
static String answer;
static String user;
static JLabel[][] grid;
static int ColCount = 0;
static int x=0;
static int y;
static ArrayList<String> tmp = new ArrayList();
Dictionary wordle = new Hashtable();
List<String> temp = Arrays.asList(getword().split(""));

//IMPORTANT NOTE:
//THE GAME WILL ONLY RUN ONCE **NO REPLAY** (THE TRUE VERSION OF WORDLE RESETS EVERYDAY (NEW YORK TIMES))
//ONLY WAY TO REPLAY IS TO RERUN THE PROGRAM!!!!

//Will open the text file full of words and add it to a dictionary. This will return a random word to be the answer
public String getword()
{
    Path path = Paths.get("C:\\Users\\stRo128Os7721\\Desktop\\NetbeansProjects\\Wordle\\assests\\Words.txt");  //NOTE: THE FILE'S DIECTORY MUST BE CHANGED TO THE 
    try                                                                                                        //DEVICES DIRECTORY OF THE "Words.txt" FILE!!!!!
    {
        dict = (ArrayList<String>) Files.readAllLines(path);
        for (int i = 0; i<=dict.size()-1;i++)                 //Adds the words from the file into a dictionary
        {
            wordle.put(i, dict.get(i));
        }
    }
    catch(Exception e)
    {
        System.out.println("Something is wrong mate: " + e);
    }
    String worldy = (String) wordle.get(r.nextInt(dict.size()));   //grabs a word from the dictionary randomly
    return worldy;
}

//The method will use the keyevent of the user to set letters into each row of labels and has limits to not go pass the row
private void SetLetter(KeyEvent e)
{
String text = String.valueOf(e.getKeyChar()).toUpperCase();
tmp.add(text);
if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
        {   
            if (y == 0) 
            {
                grid[x][y].setText("");
                y--;
            }
            else if (y<=5)
            {
                y--;
                grid[x][y].setText("");
            }
            
        }
else if (tmp.contains(text))
   {
        if (y != 5)
            {
                if (y < 5)
                    {
                        grid[x][y].setText(" " + " " + " " + " " + " " + " " + " " + text);
                        y++;
                    }
                else if (y > 5)
                    {
                        y = 0;
                    }
            }
    }

}

//Will compare the user's input to the answer and provide the corresponding operations.
private boolean isequalword(String user, List<String> b)
{ 
    String[] user_input = user.toLowerCase().split("");
    ArrayList<Boolean> isMatched = new ArrayList();
    ColCount++;
    for (int i = 0; i < 5; i++)
    {
        if (b.contains(user_input[i]))  //if the answer contains and elements from the user it will enter the other questions.
        {
            if (b.get(i).equals(user_input[i])) //if the element at the position in answer is the same as the element in the same position in user
                {                               //the label of that letter will turn green and set the text to that letter
                    grid[x][i].setOpaque(true);  
                    grid[x][i].setText(" " + " " + " " + " " + " " + " " + " " + user_input[i].toUpperCase());
                    grid[x][i].setBackground(Color.GREEN);
                    isMatched.add(true);   //the true here will trigger the winning result screen
                }
            else                //if the answer only contains that letter of the user (not in the same position)
                {               //the label will turn to yellow and set the text of the label to the letter
                    grid[x][i].setOpaque(true);
                    grid[x][i].setText(" " + " " + " " + " " + " " + " " + " " + user_input[i].toUpperCase());
                    grid[x][i].setBackground(Color.YELLOW);
                    isMatched.add(false);
                }
        }
        else  //if the user's letters are not in the answer at all
            {  //the label will turn gray and the letter is set there
                {
                    grid[x][i].setOpaque(true);
                    grid[x][i].setText(" " + " " + " " + " " + " " + " " + " " + user_input[i].toUpperCase());
                    grid[x][i].setBackground(Color.GRAY);
                    isMatched.add(false);
                }
            }
    }
    txtInput.setText("");  //after the cycle is done for the row, the user text field is set to empty
    x++;                   //the row is incremented
    y=0;                   //the row will then start at the first column
    return !isMatched.contains(false);
}

//The result screen
public void actionPerformed(KeyEvent e) 
{
    String userWord = txtInput.getText().trim();
    if (userWord.length() >= 4 && e.getKeyChar() == KeyEvent.VK_ENTER)  //if the users word is true to size and the user presses enter then;
    {                                                                   //the game will see if the user word matches the answer completely
            if (isequalword(userWord,temp))  
            {
                    JOptionPane.showMessageDialog(null, "You Win!!!", "Congrats", 1);
                    this.dispose();                                    //The result screen will then get close out of the game
                    System.out.println("Score: " + " " + (6-x));
                    return;
            }
        else if (x >= 5 && e.getKeyChar() == KeyEvent.VK_ENTER) //if the user guess doesn't match and the reach the total amount of attempts
        {                                                       //the losing result screen is shown
            JOptionPane.showMessageDialog(null, "You lose!!!", "Oops", 0);
            this.dispose();                                           ////The result screen will then get close out of the game
            System.out.println("Score: " + " " + 0);
            return;
        }
        else x--;
    }
 
    

}

    public WordleForm() {
        initComponents();
        grid = new JLabel[5][5];
        grid[0][0] = jLabel1;
        grid[0][1] = jLabel2;
        grid[0][2] = jLabel3;
        grid[0][3] = jLabel4;
        grid[0][4] = jLabel5;
        grid[1][0] = jLabel6;
        grid[1][1] = jLabel7;
        grid[1][2] = jLabel8;
        grid[1][3] = jLabel9;
        grid[1][4] = jLabel10;
        grid[2][0] = jLabel11;
        grid[2][1] = jLabel12;
        grid[2][2] = jLabel13;
        grid[2][3] = jLabel14;
        grid[2][4] = jLabel15;
        grid[3][0] = jLabel16;
        grid[3][1] = jLabel17;
        grid[3][2] = jLabel18;
        grid[3][3] = jLabel19;
        grid[3][4] = jLabel20;
        grid[4][0] = jLabel21;
        grid[4][1] = jLabel22;
        grid[4][2] = jLabel23;
        grid[4][3] = jLabel24;
        grid[4][4] = jLabel25;
//        System.out.println(temp);   
//^^^^^the answer is commented out for the game to actually work :D 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        txtInput = new javax.swing.JTextField();
        lblTITLE = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(336, 410));
        setMinimumSize(new java.awt.Dimension(336, 410));
        setPreferredSize(new java.awt.Dimension(1000, 570));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel1.setLabelFor(jLabel1);
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setIconTextGap(2);
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(280, 110, 54, 51);

        jLabel2.setLabelFor(jLabel2);
        jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2);
        jLabel2.setBounds(350, 110, 56, 51);

        jLabel3.setLabelFor(jLabel3);
        jLabel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);
        getContentPane().add(jLabel3);
        jLabel3.setBounds(420, 110, 56, 51);

        jLabel4.setLabelFor(jLabel4);
        jLabel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setOpaque(true);
        getContentPane().add(jLabel4);
        jLabel4.setBounds(490, 110, 56, 51);

        jLabel5.setLabelFor(jLabel5);
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);
        getContentPane().add(jLabel5);
        jLabel5.setBounds(560, 110, 56, 51);

        jLabel6.setLabelFor(jLabel6);
        jLabel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setOpaque(true);
        getContentPane().add(jLabel6);
        jLabel6.setBounds(280, 180, 54, 56);

        jLabel7.setLabelFor(jLabel7);
        jLabel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel7.setOpaque(true);
        getContentPane().add(jLabel7);
        jLabel7.setBounds(350, 180, 56, 56);

        jLabel8.setLabelFor(jLabel8);
        jLabel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel8.setOpaque(true);
        getContentPane().add(jLabel8);
        jLabel8.setBounds(420, 180, 56, 56);

        jLabel9.setLabelFor(jLabel9);
        jLabel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel9.setOpaque(true);
        getContentPane().add(jLabel9);
        jLabel9.setBounds(490, 180, 56, 56);

        jLabel10.setLabelFor(jLabel10);
        jLabel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel10.setOpaque(true);
        getContentPane().add(jLabel10);
        jLabel10.setBounds(560, 180, 56, 56);

        jLabel11.setLabelFor(jLabel11);
        jLabel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel11.setOpaque(true);
        getContentPane().add(jLabel11);
        jLabel11.setBounds(280, 250, 54, 56);

        jLabel12.setLabelFor(jLabel12);
        jLabel12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel12.setOpaque(true);
        getContentPane().add(jLabel12);
        jLabel12.setBounds(350, 250, 56, 56);

        jLabel13.setLabelFor(jLabel13);
        jLabel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel13.setOpaque(true);
        getContentPane().add(jLabel13);
        jLabel13.setBounds(420, 250, 56, 56);

        jLabel14.setLabelFor(jLabel14);
        jLabel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel14.setOpaque(true);
        getContentPane().add(jLabel14);
        jLabel14.setBounds(490, 250, 56, 56);

        jLabel15.setLabelFor(jLabel15);
        jLabel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel15.setOpaque(true);
        getContentPane().add(jLabel15);
        jLabel15.setBounds(560, 250, 56, 56);

        jLabel16.setLabelFor(jLabel16);
        jLabel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel16.setOpaque(true);
        getContentPane().add(jLabel16);
        jLabel16.setBounds(280, 320, 54, 56);

        jLabel17.setLabelFor(jLabel17);
        jLabel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel17.setOpaque(true);
        getContentPane().add(jLabel17);
        jLabel17.setBounds(350, 320, 56, 56);

        jLabel18.setLabelFor(jLabel18);
        jLabel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel18.setOpaque(true);
        getContentPane().add(jLabel18);
        jLabel18.setBounds(420, 320, 56, 56);

        jLabel19.setLabelFor(jLabel19);
        jLabel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel19.setOpaque(true);
        getContentPane().add(jLabel19);
        jLabel19.setBounds(490, 320, 56, 56);

        jLabel20.setLabelFor(jLabel20);
        jLabel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel20.setOpaque(true);
        getContentPane().add(jLabel20);
        jLabel20.setBounds(560, 320, 56, 56);

        jLabel21.setLabelFor(jLabel21);
        jLabel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel21.setOpaque(true);
        getContentPane().add(jLabel21);
        jLabel21.setBounds(280, 390, 54, 56);

        jLabel22.setLabelFor(jLabel22);
        jLabel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel22.setOpaque(true);
        getContentPane().add(jLabel22);
        jLabel22.setBounds(350, 390, 56, 56);

        jLabel23.setLabelFor(jLabel23);
        jLabel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel23.setOpaque(true);
        getContentPane().add(jLabel23);
        jLabel23.setBounds(420, 390, 56, 56);

        jLabel24.setLabelFor(jLabel24);
        jLabel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel24.setOpaque(true);
        getContentPane().add(jLabel24);
        jLabel24.setBounds(490, 390, 56, 56);

        jLabel25.setLabelFor(jLabel25);
        jLabel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel25.setOpaque(true);
        getContentPane().add(jLabel25);
        jLabel25.setBounds(560, 390, 56, 56);

        lblTitle.setFont(new java.awt.Font("Myanmar Text", 1, 24)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(0, 51, 51));
        lblTitle.setText("WORDLE");
        getContentPane().add(lblTitle);
        lblTitle.setBounds(400, 70, 130, 46);

        txtInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtInputKeyPressed(evt);
            }
        });
        getContentPane().add(txtInput);
        txtInput.setBounds(280, 460, 340, 20);

        lblTITLE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/wordle/word.jpeg"))); // NOI18N
        getContentPane().add(lblTITLE);
        lblTITLE.setBounds(0, 0, 1020, 570);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtInputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtInputKeyPressed
        int key = evt.getKeyCode();
                switch(key)
                {
                      case KeyEvent.VK_A:
                      SetLetter(evt); break;
                      case KeyEvent.VK_B:
                      SetLetter(evt); break;
                      case KeyEvent.VK_C:
                      SetLetter(evt); break;
                      case KeyEvent.VK_D:
                      SetLetter(evt); break;
                      case KeyEvent.VK_E:
                      SetLetter(evt); break;
                      case KeyEvent.VK_F:
                      SetLetter(evt); break;
                      case KeyEvent.VK_G:
                      SetLetter(evt); break;
                      case KeyEvent.VK_H:
                      SetLetter(evt); break;
                      case KeyEvent.VK_I:
                      SetLetter(evt); break;
                      case KeyEvent.VK_J:
                      SetLetter(evt); break;
                      case KeyEvent.VK_K:
                      SetLetter(evt); break;
                      case KeyEvent.VK_L:
                      SetLetter(evt); break;
                      case KeyEvent.VK_M:
                      SetLetter(evt); break;
                      case KeyEvent.VK_N:
                      SetLetter(evt); break;
                      case KeyEvent.VK_O:
                      SetLetter(evt); break;
                      case KeyEvent.VK_P:
                      SetLetter(evt); break;
                      case KeyEvent.VK_Q:
                      SetLetter(evt); break;
                      case KeyEvent.VK_R:
                      SetLetter(evt); break;
                      case KeyEvent.VK_S:
                      SetLetter(evt); break;
                      case KeyEvent.VK_T:
                      SetLetter(evt); break;
                      case KeyEvent.VK_U:
                      SetLetter(evt); break;
                      case KeyEvent.VK_V:
                      SetLetter(evt); break;
                      case KeyEvent.VK_W:
                      SetLetter(evt); break;
                      case KeyEvent.VK_X:
                      SetLetter(evt); break;
                      case KeyEvent.VK_Y:
                      SetLetter(evt); break;
                      case KeyEvent.VK_Z:
                      SetLetter(evt); break;
                      case KeyEvent.VK_BACK_SPACE:
                      SetLetter(evt); break;
                      case KeyEvent.VK_ENTER:
                      {
                          user = txtInput.getText();
                          actionPerformed(evt);
                          isequalword(user,temp);   
                      }                      
                }
//                System.out.println((char)key);

                
    }//GEN-LAST:event_txtInputKeyPressed

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
            java.util.logging.Logger.getLogger(WordleForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WordleForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WordleForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WordleForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WordleForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblTITLE;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField txtInput;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
