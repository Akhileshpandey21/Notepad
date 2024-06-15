import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.print.*;
import java.io.*;


public class SimpleNotepad extends JFrame implements ActionListener {
    // Declare components
    PrinterJob printerJob;
    JTextArea textArea;
    JMenuBar menuBar;
    JButton btn;
    PageFormat pageFormati;
    JMenu fileMenu, editMenu,FormatMenu,Helpmenu,ZoomMenu;
    JMenuItem newItem, openItem, saveItem, cutItem, copyItem, FontItem,ColorItem,HelpItem,FontSizeItem,pasteItem,ExitItem,printItem,SearchItem,AboutItem,FeedbackItem,PageSetupItem,ZoomInItem,ZoomOutItem;
    private static final int INITIAL_FONT_SIZE = 14;
    private int currentFontSize = INITIAL_FONT_SIZE;

    public SimpleNotepad() {
        // Set up the frame
        setTitle("Notepad");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon icon=new ImageIcon("C:\\Users\\hp\\IdeaProjects\\Random\\src\\p2.png");
        setIconImage(icon.getImage());


        // Initialize the text area
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, INITIAL_FONT_SIZE));

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Initialize the menu bar
        menuBar = new JMenuBar();


        // Initialize the File menu
        fileMenu = new JMenu("File");
        newItem = new JMenuItem("New");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
       // PageSetupItem = new JMenuItem("Page Setup");
        printItem = new JMenuItem("Print");
        ExitItem = new JMenuItem("Exit");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
      //  fileMenu.add(PageSetupItem);
        fileMenu.add(printItem);
        fileMenu.add(ExitItem);

        // Add action listeners to file menu items
        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
     //   PageSetupItem.addActionListener(this);
        printItem.addActionListener(this);
        ExitItem.addActionListener(this);

        // Initialize the Edit menu
        editMenu = new JMenu("Edit");
        cutItem = new JMenuItem("Cut");
        copyItem = new JMenuItem("Copy");
        pasteItem = new JMenuItem("Paste");
        SearchItem = new JMenuItem("Search");
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(SearchItem);

        // Add action listeners to edit menu items
        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        pasteItem.addActionListener(this);
        SearchItem.addActionListener(this);

        //Format Menu
        FormatMenu = new JMenu("Format");
        FontItem = new JMenuItem("Font");
//        ColorItem= new JMenuItem("Color");
//        FontSizeItem = new JMenuItem("Font size");
        ZoomInItem = new JMenuItem("Zoom in");
        ZoomOutItem = new JMenuItem("Zoom out");

        ZoomMenu =new JMenu("Zoom");
        FormatMenu.add(FontItem);
//        FormatMenu.add(ColorItem);
//        FormatMenu.add(FontSizeItem);
        FormatMenu.add(ZoomMenu);
        ZoomMenu.add(ZoomInItem);
        ZoomMenu.add(ZoomOutItem);

        // Add action listeners to Format menu items
        FontItem.addActionListener(this);
//        ColorItem.addActionListener(this);
//        FontSizeItem.addActionListener(this);
        ZoomInItem.addActionListener(this);
        ZoomOutItem.addActionListener(this);

           //Help Menu
        Helpmenu = new JMenu("Help");
        FeedbackItem = new JMenuItem("Send FeedBack");
        AboutItem = new JMenuItem("About");
        HelpItem = new JMenuItem("View Help");
        Helpmenu.add(FeedbackItem);
        Helpmenu.add(AboutItem);
        Helpmenu.add(HelpItem);

        // Add action listeners to Help menu items
        FeedbackItem.addActionListener(this);
        AboutItem.addActionListener(this);
        HelpItem.addActionListener(this);







        // Add menus to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(FormatMenu);
        menuBar.add(Helpmenu);

        // Add the menu bar to the frame
        setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        try{
//            printerJob =PrinterJob.getPrinterJob();
//            if(printerJob.printDialog()){
//                printerJob.print();
//            }else{
//                System.out.println("error");
//            }
//        }catch(PrinterException ex){
//            ex.printStackTrace();
//        }
        if (e.getSource() == printItem) {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable(new Printable() {
                public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
                    if (pageIndex > 0) {
                        return NO_SUCH_PAGE;
                    }
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    g2d.scale(0.75, 0.75); // Adjust the scale as necessary
                    textArea.printAll(g);
                    return PAGE_EXISTS;
                }
            });

            boolean doPrint = printerJob.printDialog();
            if (doPrint) {
                try {
                    printerJob.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }
        }
       else if (e.getSource() == newItem) {
            textArea.setText("");
        } else if (e.getSource() == openItem) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                    textArea.read(reader, null);
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == saveItem) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()));
                    textArea.write(writer);
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            else if (e.getSource() == printItem) {
                // Print functionality
                 printerJob = PrinterJob.getPrinterJob();
//                    printerJob.printDialog();
                    boolean do0Print = printerJob.printDialog();
                    if (do0Print) {
                        try {
                            printerJob.print();
                        }catch (PrinterException ex) {
                            ex.printStackTrace();
                        }
                    }

            }
            else if (e.getSource() == ExitItem) {
                // Exit the application
//                System.exit(0);
//                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                dispose();
            }
        } else if (e.getSource() == cutItem) {
            textArea.cut();
        } else if (e.getSource() == copyItem) {
            textArea.copy();
        } else if (e.getSource() == pasteItem) {
            textArea.paste();
        }
    else if (e.getSource() == SearchItem) {
        // Search functionality
        String searchTerm = JOptionPane.showInputDialog(this, "Enter text to search:", "Search", JOptionPane.PLAIN_MESSAGE);
        if (searchTerm != null && !searchTerm.isEmpty()) {
            highlightSearchTerm(textArea, searchTerm);
        }
    }
    else if(e.getSource()==FontItem){
            showFontSettingsDialog();
        }
        else if (e.getSource() == ZoomInItem) {
            // Zoom in functionality
            currentFontSize += 2;
            textArea.setFont(new Font("Arial", Font.PLAIN, currentFontSize));
        } else if (e.getSource() == ZoomOutItem) {
            // Zoom out functionality
            if (currentFontSize > 2) {
                currentFontSize -= 2;
                textArea.setFont(new Font("Arial", Font.PLAIN, currentFontSize));
            }
        }

}

private void highlightSearchTerm(JTextArea textArea, String searchTerm) {
    // Remove previous highlights
    removeHighlights(textArea);

    try {
        Highlighter highlighter = textArea.getHighlighter();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        String text = textArea.getText();
        int pos = 0;

        // Search for the term and highlight it
        while ((pos = text.indexOf(searchTerm, pos)) >= 0) {
            highlighter.addHighlight(pos, pos + searchTerm.length(), painter);
            pos += searchTerm.length();
        }
    } catch (BadLocationException e) {
        e.printStackTrace();
    }
}

private void removeHighlights(JTextArea textArea) {
    Highlighter highlighter = textArea.getHighlighter();
    Highlighter.Highlight[] highlights = highlighter.getHighlights();

    for (int i = 0; i < highlights.length; i++) {
        if (highlights[i].getPainter() instanceof DefaultHighlighter.DefaultHighlightPainter) {
            highlighter.removeHighlight(highlights[i]);
        }
    }
}

    private void showFontSettingsDialog() {
        // Create a dialog box for font settings
        JDialog fontDialog = new JDialog(this, "Font Settings", true);
        fontDialog.setLayout(new GridLayout(5, 2));

        // Font family
        JLabel fontFamilyLabel = new JLabel("Font Family:");
        String[] fontFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox<String> fontFamilyComboBox = new JComboBox<>(fontFamilies);
        fontDialog.add(fontFamilyLabel);
        fontDialog.add(fontFamilyComboBox);

        // Font style
        JLabel fontStyleLabel = new JLabel("Font Style:");
        String[] fontStyles = {"Plain", "Bold", "Italic"};
        JComboBox<String> fontStyleComboBox = new JComboBox<>(fontStyles);
        fontDialog.add(fontStyleLabel);
        fontDialog.add(fontStyleComboBox);

        // Font size
        JLabel fontSizeLabel = new JLabel("Font Size:");
        Integer[] fontSizes = {8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36};
        JComboBox<Integer> fontSizeComboBox = new JComboBox<>(fontSizes);
        fontDialog.add(fontSizeLabel);
        fontDialog.add(fontSizeComboBox);

        // Font color
        JLabel fontColorLabel = new JLabel("Font Color:");
        JButton fontColorButton = new JButton("Choose Color");
        fontColorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Choose Font Color", Color.BLACK);
            fontColorButton.putClientProperty("selectedColor", color);
        });
        fontDialog.add(fontColorLabel);
        fontDialog.add(fontColorButton);

        // OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            String selectedFontFamily = (String) fontFamilyComboBox.getSelectedItem();
            int selectedFontStyle = fontStyleComboBox.getSelectedIndex();
            int selectedFontSize = (Integer) fontSizeComboBox.getSelectedItem();
            Color selectedFontColor = (Color) fontColorButton.getClientProperty("selectedColor");

            Font newFont = new Font(selectedFontFamily, selectedFontStyle, selectedFontSize);
            textArea.setFont(newFont);
            textArea.setForeground(selectedFontColor != null ? selectedFontColor : Color.BLACK);
            fontDialog.dispose();
        });
        fontDialog.add(new JLabel());  // Empty label to align the OK button
        fontDialog.add(okButton);

        fontDialog.pack();
        fontDialog.setVisible(true);
    }



public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleNotepad().setVisible(true);
            }
        });
    }
}
