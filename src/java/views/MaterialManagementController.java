/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Colour;
import domain.Material;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.MaterialService;

/**
 * AccountController
 *
 * @author Haseeb Sheikh ID: 000687159
 *
 * Material Management Controller for 3D Printing Intake System
 */
public class MaterialManagementController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if SQL errors occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MaterialService ms = new MaterialService();
        ArrayList<Material> materials = new ArrayList<>();
        //Try-Catch that tries to populate ArrayList materials by getting all the materials information, and catches any SQL Exception errors
        try {
            materials = ms.getAllMaterials();
        } catch (SQLException ex) {
            Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
            return;
        }

        String action = request.getParameter("action");
        //If statement used to check if action is not null and equals edit, 
        if (action != null && action.equals("edit")) {
            String materialIDs = request.getParameter("materialSelected");
            int materialID = Integer.parseInt(materialIDs);
            //Try-Catch method that tries to populate material object by getting the material information by material ID selected to edit 
            //And catches any SQL Exception errors
            try {
                Material material = ms.getMaterial(materialID);
                materials.add(material);
                request.setAttribute("materials", materials);
            } catch (Exception ex) {
                Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("errorMessage", ex.getMessage());
                request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                return;
            }
        }

        request.setAttribute("materials", materials);
        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws SQLException if SQL errors occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String materialName = request.getParameter("materialName");
        String materialDesc = request.getParameter("materialDescription");
        String printerName = request.getParameter("printerName");
        String status = request.getParameter("status");
        double materialCost = 0;

        Colour materialColour = null;
        int materialID = 0;

        //Material Service used for functions
        MaterialService ms = new MaterialService();
        //Try-Catch used to try and run the switch and catches any exception errors
        try {
            //switch that is dependent on action
            switch (action) {
                //Case used to add a material
                case "add":
                    //if the variables are not empty, the material gets created and added to the system
                    System.out.println(materialName + materialCost + printerName + materialDesc);
                    materialCost = Double.parseDouble(request.getParameter("materialCost"));
                    //If statement used to verify that fields are not empty
                    if (!(materialName == null || materialName.equals("")) && !(materialDesc == null || materialDesc.equals(""))
                            && !(printerName == null || printerName.equals("")) && (materialCost != 0)) {
                        ms.createMaterial(materialName, materialDesc, printerName, materialColour, materialCost, status);
                        ArrayList<Material> materials = (ArrayList<Material>) request.getAttribute("materials");
                        materials = ms.getAllMaterials();
                        request.setAttribute("materials", materials);
                        request.setAttribute("successMessage", "New Material added.");
                        getServletContext().getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    } //Else display error message
                    else {
                        request.setAttribute("errorMessage", "Please enter the required fields.");
                        getServletContext().getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                //Case used to change the status of a colour for a material
                case "changeColourStatus":
                    //If statement used to check if materialID is null, else and display error message
                    if (request.getParameter("materialId") == null || request.getParameter("materialId").equals("")) {
                        request.setAttribute("errorMessage", "Error Editing Colour: Unable to get Material. Please try again.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                    //Colour toEdit object used to edit colour status
                    Colour toEdit = null;
                    materialID = Integer.parseInt(request.getParameter("materialId"));
                    String colourStatusName = request.getParameter("colourName");
                    //For loop that populates m Material object with all the Materials
                    for (Material m : ms.getAllMaterials()) {
                        //If statement to verify materialID and colours is populated and not null
                        if (m.getMaterialId() == materialID && m.getColours() != null) {
                            //For loop used to populate colour c object with m object colours data
                            for (Colour c : m.getColours()) {
                                //If statement used to verify the colour c object equals the colour status name variable
                                if (c.getColor().equals(colourStatusName)) {
                                    //Populates toEdit with colour c object
                                    toEdit = c;
                                }
                            }
                        }
                    }
                    //If statement that checks if toEdit is null to display error message
                    if (toEdit == null) {
                        request.setAttribute("errorMessage", "Error Editing Colour: Unable to change colour.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                    //If statement that gets toEdit object status and checks if it is equal to given input
                    if (toEdit.getStatus().equals("in-stock")) {
                        //Service object used to change colour status from previous variables
                        ms.changeColourStatus(materialID, colourStatusName, "out-of-stock");
                        request.setAttribute("successMessage", "Successfully changed status of colour <b>" + colourStatusName + "</b> to <b>Out of Stock</b>.");
                    }
                    //If statement that gets toEdit object status and checks if it is equal to given input
                    if (toEdit.getStatus().equals("out-of-stock")) {
                        //Service object used to change colour status from previous variables
                        ms.changeColourStatus(materialID, colourStatusName, "in-stock");
                        request.setAttribute("successMessage", "Successfully changed status of colour <b>" + colourStatusName + "</b> to <b>In Stock</b>.");
                    }
                    //Try-Catch to try and set materials attribute or catch any SQL Exception errors
                    try {
                        request.setAttribute("materials", ms.getAllMaterials());
                    } catch (SQLException ex) {
                        Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("errorMessage", ex.getMessage());
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                    request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    return;
                //case used to add colour
                case "addColour":
                    //Try-Catch to try and set materials attribute or catch any SQL Exception errors
                    try {
                        request.setAttribute("materials", ms.getAllMaterials());
                    } catch (SQLException ex) {
                        Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("errorMessage", ex.getMessage());
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }

                    String colourName = request.getParameter("colourName");
                    //If statement used to display error message if materialID is null
                    if (request.getParameter("materialId") == null || request.getParameter("materialId").equals("")) {
                        request.setAttribute("errorMessage", "Error Creating Colour: Unable to get Material. Please try again.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }

                    materialID = Integer.parseInt(request.getParameter("materialId"));
                    //If statement used to display error message if materialID and colourName is null
                    if (materialID == 0 || colourName == null || colourName.equals("")) {
                        request.setAttribute("errorMessage", "Error Creating Colour: Please enter all of the required fields.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }

                    Material toAdd = ms.getMaterial(materialID);
                    //For loop to check if colourName already exists
                    for (Colour c : toAdd.getColours()) {
                        if (c.getColor().equals(colourName)) {
                            request.setAttribute("errorMessage", "Error Creating Colour: The colour: <b>" + colourName + "</b> already exists for the selected material.");

                            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                            return;
                        }
                    }
                    //Starts a new ms MaterialService object
                    ms = new MaterialService();
                    //ms object adds a new colour using materialID and colour name
                    ms.addMaterialColour(materialID, colourName);
                    request.setAttribute("successMessage", "Successfully added new colour <b>" + colourName + "</b> for Material ID: <b>" + materialID + "</b>");
                    //Try-Catch to try and set materials attribute or catch any SQL Exception errors
                    try {
                        request.setAttribute("materials", ms.getAllMaterials());
                    } catch (SQLException ex) {
                        Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("errorMessage", ex.getMessage());
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                    request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    return;
                //case used to edit naterial
                case "edit":
                    //If statement used to display error message if materialID is null
                    if (request.getParameter("materialId") == null || request.getParameter("materialId").equals("")) {
                        request.setAttribute("errorMessage", "Error Creating Colour: Unable to get Material. Please try again.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                    materialID = Integer.parseInt(request.getParameter("materialId"));
                    
                    //If statement used to verify variables are not null or empty else display error message
                    if (!(materialName == null || materialName.equals("")) && !(materialDesc == null || materialDesc.equals(""))) {
                        Material material = ms.getMaterial(materialID);
                        material.setDescription(materialDesc);
                        material.setName(materialName);
                        ms.updateMaterial(material);
                        
                        //Try-Catch to try and set materials attribute or catch any SQL Exception errors
                        try {
                            request.setAttribute("materials", ms.getAllMaterials());
                        } catch (SQLException ex) {
                            Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            request.setAttribute("errorMessage", ex.getMessage());
                            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                            return;
                        }
                        
                        request.setAttribute("sucessMessage", "Material <b>" + materialName + "</b> has been updated.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    } else {
                        request.setAttribute("errorMessage", "Please enter all of the required fields.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                //case used to delete **We arent using this??
                case "delete":
                    String matID = request.getParameter("materialId");
                    materialID = Integer.parseInt(matID);
                    //If statement that verifies materialID is not populated then displays error message
                    if (materialID == 0) {
                        request.setAttribute("errorMessage", "Can't delete this material.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    } //Else material object gets material information using materialID
                    else {
                        Material material = ms.getMaterial(materialID);
                        //ms object deletes material using material object
                        ms.deleteMaterial(material);
                        request.setAttribute("successMessage", "Material has been deleted.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
            }
        } catch (Exception ex) {
            Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
            return;
        }
    }
}
