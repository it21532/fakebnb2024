package gr.hua.dit.ds.ds_lab_2024.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Retrieve error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        model.addAttribute("status", status);
        model.addAttribute("error", "An unexpected error occurred");
        model.addAttribute("message", errorMessage != null ? errorMessage : "No further details available");

        // Return the custom error view (error.html)
        return "error";
    }
}