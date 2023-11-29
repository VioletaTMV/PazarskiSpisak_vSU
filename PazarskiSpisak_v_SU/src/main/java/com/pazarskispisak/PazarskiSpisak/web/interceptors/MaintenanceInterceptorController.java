package com.pazarskispisak.PazarskiSpisak.web.interceptors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MaintenanceInterceptorController {

    @GetMapping("/maintenance")
    public String displayMaintenance(){

        return "maintenance-downtime";
    }
}
