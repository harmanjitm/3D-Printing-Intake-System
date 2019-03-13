/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import domain.Order;
import domain.OrderQueue;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.OrderQueueService;

/**
 *
 * @author 687159
 */
public class QueueManagementController extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        request.getRequestDispatcher("/WEB-INF/queueMgmt.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String qID = request.getParameter("queueID");
        String prinID = request.getParameter("printerID");
        int queueID = Integer.parseInt(qID);
        int printerID = Integer.parseInt(prinID);
        
        OrderQueueService qs = new OrderQueueService();
        
        try 
        {
            switch(action)
            {
                case "add":
                    if(!(name == null || name.equals(""))  && (queueID != 0) && (printerID != 0))
                    {          
                        qs.createQueue(name, queueID, printerID);
                        ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
                        orders.add(queue);
                        request.setAttribute("orders", orders);
                        request.setAttribute("successMessage", "New Material added.");
                        getServletContext().getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);   
                    }
                    else
                    {
                        request.setAttribute("errorMessage", "Please enter the required fields.");
                        getServletContext().getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    }
                    break;
                case "edit":
                    if(!(name == null || name.equals(""))  && (queueID != 0) && (printerID != 0))
                    {
                        for(Order editOrder: qs.getAllMaterials())
                        {
                            System.out.println(editMaterial.getMaterialId());
                            if(editMaterial.getName().equals(materialName))
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
                    }
                    else
                    {
                        request.setAttribute("errorMessage", "Please enter all of the required fields.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    }
                    break;
                case "delete":
                    String matID = request.getParameter("materialID");
                    materialID = Integer.parseInt(matID);
                    if(materialStatus = false)
                    {
                        request.setAttribute("errorMessage", "Material is out of Stock and cannot be deleted.");
                        request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                    }
                    else
                    {
                        if (materialID == 0)
                        {
                            request.setAttribute("errorMessage", "Can't delete this material.");
                            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response);
                        }
                        else
                        {
                            ms.deleteMaterial(materialID);
                            request.setAttribute("sucessMessage", "Material has been deleted.");
                            request.getRequestDispatcher("/WEB-INF/materialMgmt.jsp").forward(request, response); 
                            break;
                        }
                    }                  
                default:
                    break;
            }
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(MaterialManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
