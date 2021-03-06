package com.example.rental_car.servlet;

import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

import com.example.rental_car.dao.CategoryDao;
import com.example.rental_car.dao.RentalDao;
import com.example.rental_car.dao.VehicleDao;
import com.example.rental_car.dao.UserDao;
import com.example.rental_car.entity.Category;
import com.example.rental_car.entity.Rental;
import com.example.rental_car.entity.User;
import com.example.rental_car.entity.Vehicle;

@WebServlet(name = "VehicleServlet", urlPatterns = {"/vehicle", "/newVehicle", "/insertVehicle", "/editVehicle", "/updateVehicle", "/deleteVehicle"})
public class VehicleServlet extends HttpServlet {
    private VehicleDao vehicleDao;
    private CategoryDao categoryDao;
    private UserDao userDao;
    private RentalDao rentalDao;

    public void init() {
        vehicleDao = new VehicleDao();
        categoryDao = new CategoryDao();
        userDao = new UserDao();
        rentalDao = new RentalDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        try {
            switch (path) {
                case "/newVehicle":
                    showVehicleForm(request, response, "insert");
                    break;
                case "/insertVehicle":
                    insertEditVehicle(request, response, "insert");
                    break;
                case "/editVehicle":
                    showVehicleForm(request, response, "update");
                    break;
                case "/updateVehicle":
                    insertEditVehicle(request, response, "update");
                    break;
                case "/deleteVehicle":
                    deleteVehicle(request, response);
                    break;
                default:
                    listVehicles(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void showVehicleForm(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {
        if (action == "update") {
            int idVehicle = Integer.parseInt(request.getParameter("id"));
            Vehicle current_vehicle = vehicleDao.getVehicleById(idVehicle);
            request.setAttribute("vehicle", current_vehicle);
        }
        List<Category> listCategories = categoryDao.getAllCategories();
        request.setAttribute("listCategories", listCategories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vehicle-form.jsp");
        dispatcher.forward(request, response);
    }

    private void insertEditVehicle(HttpServletRequest request, HttpServletResponse response, String action)
            throws SQLException, IOException {
        String model = request.getParameter("model");
        String manufacturer = request.getParameter("manufacturer");
        String license_plate = request.getParameter("licensePlate");
        int year_of_registration = Integer.parseInt(request.getParameter("yearOfRegistration"));
        int idCategory = Integer.parseInt(request.getParameter("category"));
        Category category = categoryDao.getCategoryById(idCategory);
        license_plate = license_plate.toUpperCase();

        if (action == "insert") {
            Vehicle new_vehicle = new Vehicle(model, manufacturer, license_plate, year_of_registration, category);
            vehicleDao.saveVehicle(new_vehicle);
            category.addVehicle(new_vehicle);
        } else if (action == "update") {
            int idVehicle = Integer.parseInt(request.getParameter("id"));
            Vehicle current_vehicle = new Vehicle(idVehicle, model, manufacturer, license_plate, year_of_registration, category);
            vehicleDao.updateVehicle(current_vehicle);
        }

        response.sendRedirect("vehicle");
    }

    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        HttpSession session = request.getSession();
        String msg = "";
        int idVehicle = Integer.parseInt(request.getParameter("id"));

        Vehicle vehicle = vehicleDao.getVehicleById(idVehicle);

        if (vehicle.getRentals().isEmpty()){
            vehicleDao.deleteVehicle(idVehicle);
            msg="Veicolo eliminato con successo.";
        } else {
            msg = "Impossibile eliminare. Veicolo prenotato da qualche utente.";
        }

        session.setAttribute("msg", msg);
        response.sendRedirect("vehicle");
    }

    private void listVehicles(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        HttpSession session = request.getSession();
        User user = userDao.getUserById((int)session.getAttribute("id"));
        List<Vehicle> listVehicles = vehicleDao.getAllVehicles();
        List<Rental> listRentals = rentalDao.getAllRentals();
        request.setAttribute("user", user);
        request.setAttribute("listVehicles", listVehicles);
        request.setAttribute("listRentals", listRentals);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vehicle-list.jsp");
        dispatcher.forward(request, response);
    }
}
