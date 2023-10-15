import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ContratService {

  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  getContrats() {
    return this.httpClient.get(this.url + "/contrat/");
  }

}
