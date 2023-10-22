import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { tap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root',
})
export class UserService {
  url = environment.apiUrl;
  constructor(private httpClient: HttpClient) {}

  signUp(data: any) {
    return this.httpClient.post(this.url + '/inscription', data, {
      headers: new HttpHeaders().set('content-type', 'application/json')
    })
  }

  forgotPassword(data: any) {
    return this.httpClient.post(this.url + '/forgotPassword', data, {
      headers: new HttpHeaders().set('content-Type', 'application/json')
    })
  }
  
  login(data: any) {
    return this.httpClient.post(this.url + '/login', data, {
      headers: new HttpHeaders().set('content-Type', 'application/json')
    })
  }

  checkToken(){
    return this.httpClient.get(this.url+"/checkToken");
  }

  changePassword(data:any){
    return this.httpClient.post(this.url + 
      "/changePassword", data, {
      headers: new HttpHeaders().set('content-Type', 'application/json')
    })
  }

  getUsers() {
    return this.httpClient.get(this.url+"/user/ ")
  }

  update(data:any) {
    return this.httpClient.post(this.url + "/user/update",data, {
      headers:new HttpHeaders().set('Content-Type',"application/json")
    })
  }

  delete(id:any) {
    return this.httpClient.post(this.url + "/user/delete/"+id, {
      headers:new HttpHeaders().set('Content-Type',"application/json")
    })
  }
}
