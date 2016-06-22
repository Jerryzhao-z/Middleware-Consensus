package app;
import static spark.Spark.*;
import java.util.concurrent.ConcurrentSkipListSet;
import com.mashape.unirest.http.*;
import com.mashape.unirest.request.*;
import com.mashape.unirest.http.exceptions.*;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

//8000 is master, the others are slaves
public class Main {
    public static void main(String[] args)
    {
		if(args.length < 1)
			System.exit(0);
		int num_port = Integer.parseInt(args[0]);
		port(num_port);
		ConcurrentSkipListSet<Integer> slaves = new ConcurrentSkipListSet<Integer>();	
		if(num_port == 8000)
		{
			
			//code for master
			Database base = new Database();
			get("/hello", (req, res) -> "Hello World");

			get("/data/:key", (req, res) -> {
				return base.get(req.params(":key"));
			});
			//curl -X PUT -d "memory" http://localhost:8000/data/mem
			put("/data/:key", (req, res) -> {
				try{
					//ConcurrentTask task = new ConcurrentTask();
					//System.out.println("execution time: "+Long.toString(task.commit(slaves, req.params(":key"), req.body())));
					
					slaves.stream().forEach((i) -> 
					{
						String test = "";
						while(!"OK".equals(test))
						{
							test = put_message(
									"http://localhost:"+Integer.toString(i)+"/data/"+req.params(":key")+"/cancommit",
									req.body());
						}      
					});
					System.out.println("all of slaves are prepared");
					slaves.stream().forEach((i) -> 
					{
						String test = "";
						while(!"OK".equals(test))
						{
							test = put_message(
									"http://localhost:"+Integer.toString(i)+"/data/"+req.params(":key")+"/commit", "");
						}      
					});
					System.out.println("all of slaves have lanced thier timer for commit");
					slaves.stream().forEach((i) -> 
					{
						String test = "";
						while(!"OK".equals(test))
						{
							test = put_message(
									"http://localhost:"+Integer.toString(i)+"/data/"+req.params(":key")+"/docommit", "");
						}      
					});
					System.out.println("all of slaves have done commit");
				}catch(Exception e)
				{
						e.printStackTrace();
				}
				return base.insert(req.params(":key"), req.body());
			});

			delete("/data/:key", (req, res) ->{
				return base.delete(req.params(":key"));
			});
			
			post("/slaves", (req, res)-> {
				slaves.add(Integer.parseInt(req.body()));
				return "OK";
			});
			
            get("/slaves", (req, res)-> {
                String resString = "";
                for(Integer i: slaves)
                    resString = resString+'\n'+Integer.toString(i);
                return resString;
            });
			
		}else
		{
			//code for slave
			Database base = new Database();
			Database buffer = new Database();
		    ConcurrentHashMap<String, Timer> timers = new ConcurrentHashMap<String, Timer>();
			
			String register = "";
			while(!register.equals("OK"))
			{
				register = post_message("http://localhost:8000/slaves", Integer.toString(num_port));
				System.out.println(register);
			}
			System.out.println("Connected!");
			get("/hello", (req, res) -> "Hello World");

			get("/data/:key", (req, res) -> {
				return base.get(req.params(":key"));
			});
			
			
			put("/data/:key/cancommit", (req, res) -> {
				return buffer.insert(req.params(":key"), req.body());
			});
			
			put("/data/:key/commit", (req, res) -> {
				if(buffer.containsKey(req.params(":key")))
				{
					//lancer un timer
					Timer singleTimer = new Timer(req.params(":key"));
					singleTimer.schedule(new TimerTask(){
						  public void run()
						  {
							String key = req.params(":key");
							String value = buffer.remove(key);
							base.insert(key, value);
						  }					
						}, 20000);
					timers.put(req.params(":key"), singleTimer);
					
					//si timeout: insert
					return "OK";
				}
				else
					return "NO";
			});
			
			put("/data/:key/docommit", (req, res) -> {
				//si recu, timer delete
				Timer singleTimer = timers.remove(req.params(":key"));
				singleTimer.cancel();
				
				String value = buffer.remove(req.params(":key"));
				return base.insert(req.params(":key"), value);
			});

			delete("/data/:key", (req, res) ->{
				return base.delete(req.params(":key"));
			});
		}

    }

	public static String post_message(String url, String body)
	{
            String res = "";
            try{
				res = Unirest.post(url).body(body).asString().getBody();
            }catch(UnirestException e)
            {
                res = "Failed";
            }finally
            {
                return res;
            }
	}
        
    public static String put_message(String url, String body)
	{
            String res = "";
            try{
		res = Unirest.put(url).body(body).asString().getBody();
            }catch(UnirestException e)
            {
                res = "Failed";
            }finally
            {
                return res;
            }
	}
       public static String delete_message(String url)
	{
            String res = "";
            try{
		res = Unirest.delete(url).asString().getBody();
            }catch(UnirestException e)
            {
                res = "Failed";
            }finally
            {
                return res;
            }
	}

}
