import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer, CustomerRegistration} from "../../models/customer";

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private readonly API = environment.API_URL + "v1/customers";

  constructor(private http: HttpClient) {
  }

  getAllCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(this.API);
  }

  saveCustomer(customer: CustomerRegistration) {
    return this.http.post<string>(this.API, customer);
  }
}
