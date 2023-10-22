import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EtronService {

  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  choisirFormuleAbonnement(data: any) {
    return this.httpClient.post(this.url + '/choisirplan', data, {
      headers: new HttpHeaders().set('content-type', 'application/json')
    })
  }

  payerContrat(data: any) {
    return this.httpClient.post(this.url + '/paiement', data, {
      headers: new HttpHeaders().set('content-type', 'application/json')
    })
  }

  demanderFacture(data: any) {
    return this.httpClient.post(this.url + '/calculerFactureMensuelle', data, {
      headers: new HttpHeaders().set('content-type', 'application/json')
    })
  }

  payerFacture(){
    return this.httpClient.get(this.url+"/paiementFacture");
  }

  listNearbyBornes(data: any) {
    return this.httpClient.post(this.url + '/getBornes', data, {
      headers: new HttpHeaders().set('content-type', 'application/json')
    })
  }

}
