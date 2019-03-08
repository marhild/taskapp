package com.example.taskapp.controller;

import com.example.taskapp.model.Status;
import com.example.taskapp.model.Task;
import com.example.taskapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;


/**
 * @author platoiscoding.com
 */
@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * GET all tasks from Database
     * @return template view for all tasks
     */
    @RequestMapping(value = {"/tasks", "/"}, method=RequestMethod.GET)
    public String dashboard(Model model) {
        //display all Tasks
        Set<Task> tasks = taskService.getTasks();
        model.addAttribute("tasks", tasks);
        //newTask Form
        Task newTask = new Task();
        model.addAttribute("newTask", newTask);

        Set<Status> statusList = new HashSet<>();
        Status.stream().forEach(statusList::add);
        model.addAttribute("statusList", statusList);

        return "index";
    }

    /**
     * Shows Tasks by Status
     * @param model         contains TaskObject
     * @param taskStatus    may have the values "open/closed/reopened"
     * @return              Set of Tasks with specific status
     */
    @RequestMapping(value = "/{status}", method=RequestMethod.GET)
    public String displayByStatus(Model model, @PathVariable("status") String taskStatus) {

        if(taskStatus.equals(Status.OPEN.getTypeOfStatus())){
            model.addAttribute("tasks", taskService.getTasksByStatus(Status.OPEN));
        } else if(taskStatus.equals(Status.CLOSED.getTypeOfStatus())){
            model.addAttribute("tasks", taskService.getTasksByStatus(Status.CLOSED));
        } else if(taskStatus.equals(Status.REOPENED.getTypeOfStatus())){
            model.addAttribute("tasks", taskService.getTasksByStatus(Status.REOPENED));
        }

        //for newTask Form
        Task newTask = new Task();
        model.addAttribute("newTask", newTask);

        Set<Status> statusList = new HashSet<>();
        Status.stream().forEach(statusList::add);
        model.addAttribute("statusList", statusList);

        return "index";
    }

    /**
     * handles Status Changes
     * @param taskId        Task Id
     * @param action        may contain "close/open/reopen"
     * @param request       helps redirect to previous site
     * @return              redirection
     */
    @RequestMapping(value = "/task/{id}/{action}", method=RequestMethod.GET)
    public String handleStatus(@PathVariable("id") Long taskId,
                               @PathVariable("action") String action,
                               HttpServletRequest request){
        Task task = taskService.findById(taskId);

        if (action.equals("close")) {
            if(task.getStatus() == Status.OPEN) {taskService.closeTask(taskId);}
            if(task.getStatus() == Status.REOPENED) {taskService.closeTask(taskId);}
        }
        if(action.equals("reopen") && task.getStatus() == Status.CLOSED) {taskService.reopenTask(taskId);}

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    /**
     * Save NEW Task in Database
     * @param taskDetails   field values
     * @return              redirect to Dashboard
     */
    @RequestMapping(path = "/task/create", method = RequestMethod.POST)
    public String createTask(Task taskDetails) {
        Task newTask = taskService.createTask(taskDetails);
        return "redirect:/";
    }

    /**
     * Displays an EDIT Form for a Task
     * @param model         task Object
     * @param taskId        Id of the Task
     * @return              edit Form
     */
    @RequestMapping(value = "/task/{id}/edit", method=RequestMethod.GET)
    public String editForm(Model model, @PathVariable("id") Long taskId) {
        Set<Status> statusList = new HashSet<>();
        Status.stream().forEach(statusList::add);
        model.addAttribute("statusList", statusList);
        model.addAttribute("editTask", taskService.findById(taskId));
        return "editView";
    }

    /**
     * Update Task and save changes in Database
     * @param taskId        TaskId
     * @param taskDetails   field values
     * @return              redirect to Dashboard
     */
    @RequestMapping(path = "/task/{id}/update", method = RequestMethod.POST)
    public String updateTask(@PathVariable("id") long taskId, Task taskDetails) {
        taskService.updateTask(taskId, taskDetails);
        return "redirect:/";
    }

    /**
     * Deletes Task from Database
     * @param taskId    TaskId
     * @return          redirect to Dashboard
     */
    @RequestMapping(path = "/task/{id}/delete", method = RequestMethod.GET)
    public String deleteTask(@PathVariable("id") long taskId, HttpServletRequest request) {
        taskService.deleteTask(taskId);
        return "redirect:/";
    }

}
