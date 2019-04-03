/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import domain.Colour;
import domain.Material;
import domain.Printer;
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
 *
 * @author 687159
 */
public class MaterialManagementController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MaterialService ms = new MaterialService();
        //ArrayList used to populate all the materials in the materials table
        ArrayList<Material> materials = new ArrayList<>();
        try {
            materials = ms.getAllMaterials();
        } catch (SQLException ex) {
            Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
            return;
        }

        //Used to edit a material through GET
        String action = request.getParameter("action");
        if (action != null && action.equals("edit")) {
            String matID = request.getParameter("materialSelected");
            int materialID = Integer.parseInt(matID);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Request variables from the Front end to populate them
        String action = request.getParameter("action");
        String materialName = request.getParameter("materialName");
        String materialDesc = request.getParameter("materialDesc");
        String printerName = request.getParameter("printerName");
        String status = request.getParameter("status");
        double materialCost = 0;

        Colour materialColour = null;
        int materialID = 0;

        //Material Service used for functions
        MaterialService ms = new MaterialService();

        try {
            //switch that is dependent on action
            switch (action) 
            {
                //This case is used to add
                case "add":
                    materialDesc = request.getParameter("materialDescription");
                    //if the variables are not empty, the material gets created and added to the system
                    materialCost = Double.parseDouble(request.getParameter("materialCost"));
                    if (!(materialName == null || materialName.equals("")) && !(materialDesc == null || materialDesc.equals(""))
                            && !(printerName == null || printerName.equals("")) && (materialCost != 0)) {
                        ms.createMaterial(materialName, materialDesc, printerName, materialColour, materialCost, status);
                        ArrayList<Material> materials = (ArrayList<Material>) request.getAttribute("materials");
                        materials = ms.getAllMaterials();
                        request.setAttribute("materials", materials);
                        request.setAttribute("successMessage", "New Material added.");
                        getServletContext().getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    } 
                    //else the program errors out
                    else {
                        request.setAttribute("errorMessage", "Please enter the required fields.");
                        getServletContext().getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                case "changeColourStatus":
                    if(request.getParameter("materialId") == null || request.getParameter("materialId").equals(""))
                    {
                        request.setAttribute("errorMessage", "Error Editing Colour: Unable to get Material. Please try again.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                    Colour toChange = null;
                    materialID = Integer.parseInt(request.getParameter("materialId"));
                    String colourStatusName = request.getParameter("colourName");
                    for(Material m : ms.getAllMaterials())
                    {
                        if(m.getMaterialId() == materialID && m.getColours() != null)
                        {
                            for(Colour c : m.getColours())
                            {
                                if(c.getColor().equals(colourStatusName))
                                {
                                    toChange = c;
                                }
                            }
                        }
                    }
                    
                    if(toChange == null)
                    {
                        request.setAttribute("errorMessage", "Error Editing Colour: Unable to change colour.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                    
                    if(toChange.getStatus().equals("in-stock"))
                    {
                        ms.changeColourStatus(materialID, colourStatusName, "out-of-stock");
                        request.setAttribute("successMessage", "Successfully changed status of colour <b>" + colourStatusName + "</b> to <b>Out of Stock</b>.");
                    }
                    if(toChange.getStatus().equals("out-of-stock"))
                    {
                        ms.changeColourStatus(materialID, colourStatusName, "in-stock");
                        request.setAttribute("successMessage", "Successfully changed status of colour <b>" + colourStatusName + "</b> to <b>In Stock</b>.");
                    }
                    
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
                //Adding a new colour to a Material
                case "addColour":
                    try {
                        request.setAttribute("materials", ms.getAllMaterials());
                    } catch (SQLException ex) {
                        Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
                        request.setAttribute("errorMessage", ex.getMessage());
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                    
                    String colourName = request.getParameter("colourName");
                    
                    if(request.getParameter("materialId") == null || request.getParameter("materialId").equals(""))
                    {
                        request.setAttribute("errorMessage", "Error Creating Colour: Unable to get Material. Please try again.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                    
                    materialID = Integer.parseInt(request.getParameter("materialId"));
                    
                    if(materialID == 0 || colourName == null || colourName.equals(""))
                    {
                        request.setAttribute("errorMessage", "Error Creating Colour: Please enter all of the required fields.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    }
                    //Check if colour already exists
                    Material toAdd = ms.getMaterial(materialID);
                    for(Colour c : toAdd.getColours())
                    {
                        if(c.getColor().equals(colourName))
                        {
                            request.setAttribute("errorMessage", "Error Creating Colour: The colour: <b>" + colourName + "</b> already exists for the selected material.");
                            
                            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                            return;
                        }
                    }
                    
                    ms = new MaterialService();
                    ms.addMaterialColour(materialID, colourName);
                    
                    request.setAttribute("successMessage", "Successfully added new colour <b>" + colourName + "</b> for Material ID: <b>" + materialID + "</b>");
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
                //This case is used to edit a material
                case "edit":
                    materialCost = Double.parseDouble(request.getParameter("materialCost"));
                    //If the variables are not empty in the front end, it'll grab the information and edit it.
                    if (!(materialName == null || materialName.equals("")) && !(materialDesc == null || materialDesc.equals(""))
                            && !(printerName == null || printerName.equals("")) && !(materialColour == null || materialColour.equals(""))
                            && (materialCost != 0) && !(status == null || status.equals(""))) 
                    {
                        //For loop utilized to look through all the materials to find the one that is being edited.
                        for (Material editMaterial : ms.getAllMaterials()) 
                        {
                            if (editMaterial.getName().equals(materialName)) 
                            {
                                ms.updateMaterial(editMaterial);
                            }
                        }
                        request.setAttribute("sucessMessage", "Material has been updated.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    } 
                    else 
                    {
                        request.setAttribute("errorMessage", "Please enter all of the required fields.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    }
                    break;
                //This case is used to delete
                case "delete":
                    String matID = request.getParameter("materialID");
                    //Grabs the ID of the material selected
                    materialID = Integer.parseInt(matID);
                    //If material status is out of stock, the material won't be deleted
                    if (status == ("out-of-stock")) 
                    {
                        request.setAttribute("errorMessage", "Material is out of Stock and cannot be deleted.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    } 
                    //The program keeps on with the delete function
                    else 
                    {
                        //If the materialID is 0 or doesn't exist, the program won't be able to delete
                        if (materialID == 0) 
                        {
                            request.setAttribute("errorMessage", "Can't delete this material.");
                            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        } 
                        //else the program will use the materialID to delete it from the program
                        else 
                        {
                            Material material = ms.getMaterial(materialID);
                            ms.deleteMaterial(material);
                            request.setAttribute("successMessage", "Material has been deleted.");
                            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                            return;
                        }
                    }
                default:
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
            return;
        }
    }
}
