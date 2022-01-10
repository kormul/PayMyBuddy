package com.paymybuddy.PayMyBuddy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.paymybuddy.PayMyBuddy.dto.UserDto;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.UsersRepository;
import com.paymybuddy.PayMyBuddy.service.ConnectionService;
import com.paymybuddy.PayMyBuddy.service.RegisterService;
import com.paymybuddy.PayMyBuddy.service.TransactionService;
import com.paymybuddy.PayMyBuddy.service.UserService;

@Controller
public class PayMyBuddyController {
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private ConnectionService connectionService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String home() {
		return homes();
	}
	
	@GetMapping("/account")
	public String profile(HttpServletRequest httpServletRequest, Model model) {
		
		User user = userService.getUserAuthen();
		model.addAttribute("user", user);	
		model.addAttribute("transactionHistory", transactionService.getPageTransaction(1));
		return "account";
	}
	
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	@GetMapping("/home")
	public String homes() {
		if(SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
			return "home";
		}
		return "home_logged";
	}
	
	@GetMapping("/register")
	public String register(WebRequest request, Model model) {
		SecurityContextHolder.clearContext();
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
	    return "register";
	}
	
	
	@PostMapping("/register")
	public RedirectView registerUser(@ModelAttribute("user") @Validated UserDto userDto, HttpServletRequest request, Errors errors) {
		try {
			registerService.registerUser(userDto);
		} catch (Exception exception) {
			return new RedirectView("register");
		}
		return new RedirectView("login");
	}
	
    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
		SecurityContextHolder.clearContext();
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    
    @GetMapping("/add_connection")
    public String addConnection() {
    	return "add_connection";
    }
    
    @PostMapping("/add_connection")
	public RedirectView connectionSubmit(@RequestParam(value = "username", required = true) String username) {
    	connectionService.addConnection(username);
    	return new RedirectView("transfer");
	}
    
    @GetMapping("/transfer")
    public String transfer(HttpServletRequest httpServletRequest,Model model) {
    	int pageNumber = 1;
    	if (httpServletRequest.getParameter("page") != null && !httpServletRequest.getParameter("page").isEmpty()) {
    		pageNumber = Integer.parseInt(httpServletRequest.getParameter("page"));
        }
    	model.addAttribute("AllConnnection", connectionService.getConnection());
    	model.addAttribute("transactionHistory", transactionService.getPageTransaction(pageNumber));
        model.addAttribute("currentPage", pageNumber-1);
        model.addAttribute("totalPages", transactionService.getAllPage());
    	return "transfer";
    }
    
    @PostMapping("/transfer")
    public RedirectView addTransfer(@RequestParam(value = "money", required = true) double money, @RequestParam(value = "receiver", required = true) int receiver, @RequestParam(value = "description", required = true) String description) {
    	
    	if(!userService.nonDebtor(money)) {
            return new RedirectView("transfer?debtor");
    	}
    	
    	transactionService.addTransaction(money, description, receiver);
    	
    	return new RedirectView("transfer?success");
    }
    
    @GetMapping("/log_off")
    public RedirectView logOff(RedirectAttributes attributes) {
		SecurityContextHolder.clearContext();
        return new RedirectView("home");
    }
    
    @GetMapping("/provision")
    public String provision() {
    	return "provision";
    }
    
    @GetMapping("/bank")
    public String bankTransfer() {
    	return "bank";
    }
    
    @PostMapping("/add_money")
    public RedirectView addMoney(@RequestParam(value = "addmoney", required = true) double money) {
    	userService.addMoney(money);
        return new RedirectView("account");
    }
    
    @PostMapping("/take_money")
    public RedirectView takeMoney(@RequestParam(value = "takemoney", required = true) double money) {
    	if(!userService.nonDebtor(money)) {
            return new RedirectView("account?debtor");
    	}
    	userService.takeMoney(money);
    	
        return new RedirectView("account");
    }
    
}
