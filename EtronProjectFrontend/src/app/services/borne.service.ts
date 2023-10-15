import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BorneService {

  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  add(data:any) {
    return this.httpClient.post(this.url + 
      "/borne/add",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json") 
      })
  }

  update(data:any) {
    return this.httpClient.post(this.url + 
      "/borne/update",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json") 
      })
  }

  getBornes() {
    return this.httpClient.get(this.url + "/borne/");
  }

  delete(id:any) {
    return this.httpClient.post(this.url + "/borne/delete/"+id, {
      headers:new HttpHeaders().set('Content-Type',"application/json")
    })
  }
}
