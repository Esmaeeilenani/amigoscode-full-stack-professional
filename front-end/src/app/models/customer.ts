export interface Customer {

  id?: number;


  name?: string;

  email?: string;

  age?: number;


  gender?: string;
}
export interface CustomerRegistration extends Customer{

  password? :string;

}
