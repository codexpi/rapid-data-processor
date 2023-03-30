package me.rafique.core.service;

import me.rafique.core.config.AppConfig;
import me.rafique.core.data.DbAction;
import me.rafique.core.entity.Customer;
import me.rafique.core.repository.CustomerRepository;

import java.io.BufferedReader;
import java.io.FileReader;

public class CustomerServiceDbFile {
    private CustomerRepository repository;
    private CustomerServiceDb repositoryAsync;
    DbAction da=DbAction.create();

    public CustomerServiceDbFile() {
        repository=new CustomerRepository();
        repositoryAsync=new CustomerServiceDb();
    }
    public void insertCustomerFromFile(){
        try{
            da.openConnEx();
            BufferedReader br=new BufferedReader(new FileReader(AppConfig.customerDataFileName));
            String line=br.readLine();
            while(line!=null){
                String[] row=line.split(",");
                repository.createRaw(row);
                line=br.readLine();
            }
            br.close();
            da.closeConnEx();
        }catch(Exception ex){ex.printStackTrace();}
    }


}
