package tutorial;

import com.opensymphony.xwork2.ActionSupport;

public class HelloWorld extends ActionSupport {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String execute() {
        name = "Hello, " + name + "!"; 
        return SUCCESS;
    }
}