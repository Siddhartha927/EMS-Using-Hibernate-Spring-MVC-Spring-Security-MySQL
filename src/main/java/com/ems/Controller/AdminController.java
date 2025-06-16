package com.ems.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ems.Service.ClientService;
import com.ems.Service.EmployeeService;
import com.ems.Service.ProjectService;
import com.ems.dao.ProjectDAO;
import com.ems.entity.Client;
import com.ems.entity.ContactPerson;
import com.ems.entity.Employee;
import com.ems.entity.Project;

//Marks this class as a Spring MVC controller
@Controller
//Maps all requests with /adminDashboard prefix to this controller
@RequestMapping("/adminDashboard")
public class AdminController {

	 // Injects EmployeeService to handle employee-related operations
	 @Autowired
	 private EmployeeService employeeService;
	
	 // Injects ClientService to handle client-related operations
	 @Autowired
	 private ClientService clientService;
	
	 // Injects ProjectService to handle project-related operations
	 @Autowired
	 private ProjectService projectService;
	
	 // Injects ProjectDAO for direct data access related to projects
	 @Autowired
	 private ProjectDAO projectDAO;

	
	 // Handles GET request to /adminDashboard, returns admin dashboard view
	 @GetMapping()
	 public String adminDashboard() {
	     return "adminDashboard";
	 }
	
	 // Handles GET request to show the add employee form
	 @GetMapping("/add")
	 public String showAddEmployeeForm(Model model) {
	     // Adds an empty Employee object to the model for form binding
	     model.addAttribute("employee", new Employee());
	     return "addEmployee"; 
	 }
	
	 // Handles POST request to add a new employee
	 @PostMapping("/add")
	 public String addEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
	     // Checks if the email already exists
	     if(employeeService.isEmailExists(employee.getEmail())) {
	         // Adds error message to be displayed on redirect
	         redirectAttributes.addFlashAttribute("errorMessage","Email already exists....");
	         return "redirect:/adminDashboard/add";
	     }
	     // Adds the new employee using the service
	     employeeService.addEmployee(employee);
	
	     // Adds success message to be displayed on redirect
	     redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully!");
	     return "redirect:/adminDashboard/add"; 
	 }
	
	 // Handles GET request to show the list of all employees
	 @GetMapping("/employeelist")
	 public String listEmployees(Model model) {
	     // Adds all employees to the model
	     model.addAttribute("employees", employeeService.getAllEmployees());
	     return "getAllEmployees";  
	 }
	
	 // Handles GET request to show the employee search form
	 @GetMapping("/employeeById")
	 public String getEmployeeSearchform() {
	     return "employeeDetails";
	 }
	
	 // Handles POST request to search an employee by ID
	 @PostMapping("/employeesearch")
	 public String getEmployeeById(@RequestParam String employeeId, Model model) {
	     // Retrieves employee by ID
	     Employee employee = employeeService.getEmployeeById(employeeId);
	
	     // If employee not found, return error message
	     if(employee == null ) {
	         model.addAttribute("errorMessage","Employee Id don't exists....");
	         return "employeeDetails";
	     }
	
	     // Add the found employee to the model
	     model.addAttribute("employee", employee);
	     return "employeeDetails";
	 }

	// Handles GET request to delete an employee by ID
	 @GetMapping("/delete/{employeeId}")
	 public String deleteEmployee(@PathVariable String employeeId, RedirectAttributes redirectAttributes) {
	     try {
	         // Calls the service to delete the employee by ID
	         employeeService.deleteEmployee(employeeId);

	         // Adds a success message to be shown after redirect
	         redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully...");
	     } catch (Exception e) {
	         // Adds an error message if deletion fails
	         redirectAttributes.addFlashAttribute("errorMessage", "Error deleting employee : " + e.getMessage());
	     }

	     // Redirects to the admin dashboard
	     return "redirect:/adminDashboard";
	 }

	 // Handles GET request to retrieve an employee by email
	 @GetMapping("/getEmployeeByEmail")
	 public String getEmployeeByEmail(@RequestParam(required = false) String email, Model model) {

	     // Checks if email is empty or null
	     if (email == null || email.trim().isEmpty()) {
	         // Adds an error message to the model
	         model.addAttribute("errorMessage", "Please enter an email address...");
	         return "getEmployeeByEmail";
	     }

	     // Calls service to find employee by email
	     Employee employee = employeeService.getEmployeeByEmail(email);

	     // If employee is not found, show error
	     if (employee == null) {
	         model.addAttribute("errorMessage", "Employee not exists....");
	         return "getEmployeeByEmail";
	     }

	     // Adds the found employee to the model
	     model.addAttribute("employee", employee);
	     return "getEmployeeByEmail";
	 }

	 // Handles GET request to show the update form for a specific employee
	 @GetMapping("/update-employee/{employeeId}")
	 public String showEditForm(@PathVariable String employeeId, Model model) {
	     // Retrieves employee by ID
	     Employee employee = employeeService.getEmployeeById(employeeId);

	     // Adds employee object to the model to populate the form
	     model.addAttribute("employee", employee);
	     return "updateEmployee";
	 }

	 // Handles POST request to update employee details
	 @PostMapping("/update-employee")
	 public String updateEmployee(
	         @ModelAttribute Employee employee,
	         RedirectAttributes redirectAttributes) {

	     try {
	         // Calls service to update the employee
	         employeeService.updateEmployee(employee);

	         // Adds a success message on successful update
	         redirectAttributes.addFlashAttribute("successMessage", "Employee Updated Successfully..");
	         return "redirect:/adminDashboard";
	     } catch (Exception e) {
	         // Adds an error message on update failure
	         redirectAttributes.addFlashAttribute("errorMessage", "Error updating Employee.....");

	         // Redirects back to the update form with the same employee ID
	         return "redirect:/adminDashboard/update-employee/" + employee.getEmployeeId();
	     }
	 }

	// Handles GET request to show the form for adding a new client
	 @GetMapping("/addClient")
	 public String showAddClientForm(Model model) {
	     // Adds an empty Client object to the model for form binding
	     model.addAttribute("client", new Client());
	     return "addClient"; // Returns the view name to display the add client form
	 }

	 // Handles POST request to add a new client
	 @PostMapping("/addClient")
	 public String addClient(@ModelAttribute Client client, RedirectAttributes redirectAttributes) {
	     
	     // Checks if the client name already exists
	     if(clientService.isClientNameExists(client.getClientName())) {
	         // Adds an error message to be shown after redirect
	         redirectAttributes.addFlashAttribute("errorMessage","ClientName already exists....");
	         return "redirect:/adminDashboard/addClient"; // Redirects back to the add client form
	     }

	     // Calls the service to add the new client
	     clientService.addClient(client);

	     // Adds a success message to be shown after redirect
	     redirectAttributes.addFlashAttribute("successMessage", "Client added successfully!");
	     return "redirect:/adminDashboard/addClient"; // Redirects back to the add client form
	 }

	 // Handles GET request to show the client search form
	 @GetMapping("/getClientById")
	 public String getClientSearchform() {
	     return "getClientById"; // Returns the view for searching client by ID
	 }

	 // Handles POST request to retrieve a client by clientId
	 @PostMapping("/getClientById")
	 public String getClientById(@RequestParam String clientId, Model model, RedirectAttributes redirectAttributes) {
	     // Retrieves client by ID using the service
	     Client client = clientService.getClientById(clientId);

	     // If client not found, add error message and redirect to search form
	     if(client == null) {
	         redirectAttributes.addFlashAttribute("errorMessage", "Client don't exists...");
	         return "redirect:/adminDashboard/getClientById";
	     }

	     // If client found, add it to the model to display on the page
	     model.addAttribute("client", client);
	     return "getClientById"; // Returns the view to show the client details
	 }

	// Handles GET request to show the form for adding a contact person to a specific client
	 @GetMapping("/addContactPerson/{clientId}")
	 public String showAddContactPersonForm(@PathVariable String clientId, Model model) {
	     // Adds clientId to the model to use it in the form
	     model.addAttribute("clientId", clientId);
	     return "addContactPerson"; // Returns the view for adding contact person
	 }

	 // Handles POST request to add a contact person to a specific client
	 @PostMapping("/addContactPerson/{clientId}")
	 public String addContactPerson(@PathVariable String clientId, 
	                                @RequestParam String name, 
	                                @RequestParam String email,
	                                @RequestParam String phone,
	                                @RequestParam String designation,
	                                RedirectAttributes redirectAttributes) {

	     // Retrieves the client using the provided clientId
	     Client client = clientService.getClientById(clientId);

	     // If client exists, create and add the contact person
	     if (client != null) {
	         // Create a new ContactPerson object
	         ContactPerson contactPerson = new ContactPerson();

	         // Set contact person's name
	         contactPerson.setName(name);

	         // Set contact person's email
	         contactPerson.setEmail(email);

	         // Set contact person's phone
	         contactPerson.setPhone(phone);

	         // Set contact person's designation
	         contactPerson.setDesignation(designation);

	         // Associate the contact person with the client
	         contactPerson.setClient(client);

	         // Add the contact person to the client using service
	         clientService.addContactPerson(contactPerson, clientId);

	         // Add success message after successful addition
	         redirectAttributes.addFlashAttribute("successMessage", "Contact person added successfully!");
	     } else {
	         // If client not found, add error message
	         redirectAttributes.addFlashAttribute("errorMessage", "Client not found!");
	     }

	     // Redirect to the getClientById page with the current clientId
	     return "redirect:/adminDashboard/getClientById?clientId=" + clientId; 
	 }

	// Handles GET request to display all clients
	 @GetMapping("/clients")
	 public String getAllClients(Model model) {
	     // Retrieves list of all clients from the service
	     List<Client> clients = clientService.getAllClients();

	     // Adds the list of clients to the model
	     model.addAttribute("clients", clients);

	     // Returns the view to display the list of clients
	     return "clientList";
	 }

	 // Handles GET request to show the form for adding a new project
	 @GetMapping("/addProject")
	 public String showAddProjectForm(Model model) {
	     // Retrieves all clients to populate the dropdown in the form
	     List<Client> clientList = clientService.getAllClients();

	     // Creates a new Project object
	     Project project = new Project();

	     // Initializes the client inside the project object
	     project.setClient(new Client());

	     // Adds the project object to the model for form binding
	     model.addAttribute("project", project);

	     // Adds the list of clients to the model for selection
	     model.addAttribute("clients", clientList);

	     // Returns the view for the add project form
	     return "addProject";
	 }

	 // Handles POST request to add a new project
	 @PostMapping("/addProject")
	 public String addProject(@ModelAttribute Project project, RedirectAttributes redirectAttributes) {
	     try {
	         // Tries to add the project using the service
	         projectService.addProject(project);

	         // Adds a success message to be shown after redirect
	         redirectAttributes.addFlashAttribute("successMessage", "Project added successfully!");
	     } catch (Exception e) {
	         // Adds an error message if the project addition fails
	         redirectAttributes.addFlashAttribute("errorMessage", "Error in adding project");
	     }

	     // Redirects back to the add project form
	     return "redirect:/adminDashboard/addProject";
	 }

	 // Handles GET request to show the search form for a project by ID
	 @GetMapping("/getProjectById")
	 public String getProjectSearchform() {
	     // Returns the view to search project by ID
	     return "getProjectById";
	 }

	 // Handles POST request to retrieve project by projectId
	 @PostMapping("/getProjectById")
	 public String getProjectById(@RequestParam String projectId, Model model, RedirectAttributes redirectAttributes) {
	     // Retrieves the project from the service using projectId
	     Project project = projectService.getProjectById(projectId);

	     // If project not found, add error message and redirect back to search form
	     if(project == null) {
	         redirectAttributes.addFlashAttribute("errorMessage", "Project don't exists...");
	         return "redirect:/adminDashboard/getProjectById";
	     }

	     // If project is found, add it to the model to display
	     model.addAttribute("project", project);

	     // Returns the view to display project details
	     return "getProjectById";
	 }

	// Handles GET request to show the update form for a specific project
	 @GetMapping("/updateProject/{projectId}")
	 public String showProject(@PathVariable String projectId, Model model) {
	     // Fetches the project by projectId using the service
	     Project project = projectService.getProjectById(projectId);

	     // Adds the project to the model for form binding
	     model.addAttribute("project", project);

	     // Returns the view to display the update project form
	     return "updateProject";
	 }

	 // Handles POST request to update the project
	 @PostMapping("/updateProject")
	 public String updateproject(
	         @ModelAttribute Project project, // Binds the form data to the Project object
	         RedirectAttributes redirectAttributes) {

	     try {
	         // Tries to update the project using the service
	         projectService.updateProject(project);

	         // Adds a success message after successful update
	         redirectAttributes.addFlashAttribute("successMessage", "Project Updated Successfully..");

	         // Redirects to the admin dashboard
	         return "redirect:/adminDashboard";
	     } catch (Exception e) {
	         // Adds error message if update fails
	         redirectAttributes.addFlashAttribute("errorMessage", "Error updating project.....");

	         // Redirects back to the update form with the project ID
	         return "redirect:/adminDashboard/updateProject/" + project.getProjectId();
	     }
	 }

	 // Handles GET request to delete a project by its ID
	 @GetMapping("/deleteProject/{projectId}")
	 public String deleteProject(@PathVariable String projectId, RedirectAttributes redirectAttributes) {
	     try {
	         // Tries to delete the project using the service
	         projectService.deleteProject(projectId);

	         // Adds a success message after deletion
	         redirectAttributes.addFlashAttribute("successMessage", "Project deleted successfully...");
	     } catch (Exception e) {
	         // Adds error message if deletion fails
	         redirectAttributes.addFlashAttribute("errorMessage", "Error deleting project : " + e.getMessage());
	     }

	     // Redirects back to the admin dashboard
	     return "redirect:/adminDashboard";
	 }

	// Handles GET request to retrieve and display all projects
	 @GetMapping("/getAllProjects")
	 public String getAllProjects(Model model) {
	     // Adds the list of all projects to the model
	     model.addAttribute("projectList", projectService.getAllProjects());

	     // Returns the view name for displaying the list of projects
	     return "projectList";    
	 }

	 // Handles GET request to show the project assignment page for a specific employee
	 @GetMapping("/assign-project/{employeeId}")
	 public String showAssignmentPage(@PathVariable String employeeId, Model model) {
	     
	     // Adds the list of available projects to the model
	     model.addAttribute("projects", projectDAO.getAllProjects());
	     
	     // Adds the specific employee object to the model
	     model.addAttribute("employee", employeeService.getEmployeeById(employeeId));

	     // Returns the view name for assigning project to employee
	     return "assignProject";
	 }

	 // Handles POST request to assign a project to an employee
	 @PostMapping("/assignProject")
	 public String assignProject(@RequestParam String employeeId,
	                             @RequestParam String projectId,
	                             RedirectAttributes redirectAttributes) {
	     try {
	         // Tries to assign the project to the employee
	         employeeService.assignProject(employeeId, projectId);

	         // Adds a success message after successful assignment
	         redirectAttributes.addFlashAttribute("successMessage", "Project assigned successfully.");
	     } catch (Exception e) {
	         // Adds an error message if assignment fails
	         redirectAttributes.addFlashAttribute("errorMessage", "Error assigning project.");
	     }

	     // Redirects back to the assignment page for the employee
	     return "redirect:/adminDashboard/assign-project/" + employeeId;
	 }

	 // Handles GET request to remove an employee from the assigned project
	 @GetMapping("/remove-from-project/{employeeId}")
	 public String removeFromProjectc(@PathVariable String employeeId, RedirectAttributes redirectAttributes) {
	     try {
	         // Tries to remove the project assignment from the employee
	         employeeService.removeProjectFromEmployee(employeeId);

	         // Adds a success message after removal
	         redirectAttributes.addFlashAttribute("successMessage", "project remove from employee");
	     } catch (Exception e) {
	         // Prints the exception stack trace in case of error
	         e.printStackTrace();
	     }

	     // Redirects back to the assignment page for the employee
	     return "redirect:/adminDashboard/assign-project/" + employeeId;
	 }
	 
	 @GetMapping("/set-employee-password/{employeeId}")
	 public String setEmployeePassword(@PathVariable String employeeId, Model model) {
		 model.addAttribute("employee", employeeService.getEmployeeById(employeeId));
		 return "set-employee-password";
	 }
	 
	 @PostMapping("/set-employee-password")
	 public String setEmployeePassword(@RequestParam String employeeId,
			 						@RequestParam String newPassword,
			 						@RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
		 if(!newPassword.equals(confirmPassword)) {
			 redirectAttributes.addFlashAttribute("error", "password does not match");
			 return "redirect:/adminDashboard/set-employee-password/" + employeeId;
		 }
		 try {
			 employeeService.setPassword(employeeId, newPassword);
			 redirectAttributes.addFlashAttribute("success", "Password set Successfully");
		 }catch(Exception e) {
			 e.printStackTrace();
			 redirectAttributes.addFlashAttribute("passwordError", "Password not set");
			 
		 }
		 
		 return "redirect:/adminDashboard/set-employee-password/" + employeeId;
		 
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 @GetMapping("/set-client-password/{clientId}")
	 public String setClientPassword(@PathVariable String clientId , Model model) {
		 model.addAttribute("client", clientService.getClientById(clientId));
		 return "set-client-password";
	 }
	 
	 @PostMapping("/set-client-password")
	 public String setClientPassword(@RequestParam String clientId,
			 						@RequestParam String newPassword,
			 						@RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
		 if(!newPassword.equals(confirmPassword)) {
			 redirectAttributes.addFlashAttribute("error", "password does not match");
			 return "redirect:/adminDashboard/set-client-password/" + clientId;
		 }
		 try {
			 String result = clientService.setPassword(clientId, newPassword);
			 if(result.contains("no")) {
				 redirectAttributes.addFlashAttribute("error", "password not set...no email available");
			 }else {
				 redirectAttributes.addFlashAttribute("success", "Password set Successfully");
			 } 
		 }catch(Exception e) {
			 e.printStackTrace();
			 redirectAttributes.addFlashAttribute("passwordError", "Password not set");
			 
		 }
		 
		 return "redirect:/adminDashboard/set-client-password/" + clientId;
		 
	 }
	 
	 
	 
	 


}
