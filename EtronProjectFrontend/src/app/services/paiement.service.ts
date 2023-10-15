import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PaiementService {

  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  getPaiements() {
    return this.httpClient.get(this.url + "/paiement/");
  }
}
