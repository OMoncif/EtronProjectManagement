import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RechargeService {

  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  getRecharges() {
    return this.httpClient.get(this.url + "/recharge/");
  }

  add(data:any) {
    return this.httpClient.post(this.url + 
      "/recharge/add",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json") 
      })
  }
}
