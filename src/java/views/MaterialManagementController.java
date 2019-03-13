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
        ArrayList<Material> materials = new ArrayList<>();
        try {
            materials = ms.getAllMaterials();
        } catch (SQLException ex) {
            Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMessage", ex.getMessage());
            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
            return;
        }

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
            }
        }
        
        request.setAttribute("materials", materials);
        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String materialName = request.getParameter("materialName");
        String materialDesc = request.getParameter("materialDesc");
        String printerName = request.getParameter("printerName");
        String status = request.getParameter("status");
        double materialCost = Double.parseDouble(request.getParameter("materialCost"));

        Colour materialColour = null;
        int materialID = 0;

        MaterialService ms = new MaterialService();

        try {
            switch (action) {
                case "add":
                    if (!(materialName == null || materialName.equals("")) && !(materialDesc == null || materialDesc.equals(""))
                            && !(printerName == null || printerName.equals("")) && !(materialColour == null || materialColour.equals(""))
                            && (materialCost != 0)) {
                        ms.createMaterial(materialName, materialDesc, printerName, materialColour, materialCost, status);
                        ArrayList<Material> materials = (ArrayList<Material>) request.getAttribute("materials");
                        materials = ms.getAllMaterials();
                        request.setAttribute("materials", materials);
                        request.setAttribute("successMessage", "New Material added.");
                        getServletContext().getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    } else {
                        request.setAttribute("errorMessage", "Please enter the required fields.");
                        getServletContext().getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    }
                    break;
                case "edit":
                    if (!(materialName == null || materialName.equals("")) && !(materialDesc == null || materialDesc.equals(""))
                            && !(printerName == null || printerName.equals("")) && !(materialColour == null || materialColour.equals(""))
                            && (materialCost != 0) && !(status == null || status.equals(""))) {
                        for (Material editMaterial : ms.getAllMaterials()) 
                        {
                            System.out.println(editMaterial.getMaterialId());
                            if (editMaterial.getName().equals(materialName)) 
                            {
                                System.out.println(editMaterial.getMaterialId());
                                System.out.println("Finding Material");
                                ms.updateMaterial(editMaterial);
                                System.out.println("Found Material");
                            }
                        }
                        System.out.println("Done updating");
                        request.setAttribute("sucessMessage", "Material has been updated.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        return;
                    } else {
                        request.setAttribute("errorMessage", "Please enter all of the required fields.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    }
                    break;
                case "delete":
                    String matID = request.getParameter("materialID");
                    materialID = Integer.parseInt(matID);
                    if (status == ("false")) {
                        request.setAttribute("errorMessage", "Material is out of Stock and cannot be deleted.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    } else {
                        if (materialID == 0) {
                            request.setAttribute("errorMessage", "Can't delete this material.");
                            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        } else 
                        {
                            Material material = ms.getMaterial(materialID);
                            ms.deleteMaterial(material);
                            request.setAttribute("sucessMessage", "Material has been deleted.");
                            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                            break;
                        }
                    }
                default:
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
