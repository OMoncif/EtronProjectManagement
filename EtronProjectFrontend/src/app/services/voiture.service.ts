import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VoitureService {

  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  add(data:any) {
    return this.httpClient.post(this.url + 
      "/voiture/add",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json") 
      })
  }

  update(data:any) {
    return this.httpClient.post(this.url + 
      "/voiture/update",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json") 
      })
  }

  getVoitures() {
    return this.httpClient.get(this.url + "/voiture/");
  }

  delete(id:any) {
    return this.httpClient.post(this.url + "/voiture/delete/"+id, {
      headers:new HttpHeaders().set('Content-Type',"application/json")
    })
  }
}
