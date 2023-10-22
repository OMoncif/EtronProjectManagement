import { Injectable } from "@angular/core";

export interface Menu {
    state:string;
    name:string;
    type:string;
    icon:string;
    role:string;
}

const MENUITEMS = [
    {state:'dashboard',name:'Dashboard',type:'link',icon:'dashboard',role:''},
    {state:'abonnement',name:'Manage Plans',type:'link',icon:'category',role:'admin'},
    {state:'bornesproches',name:'Bornes Proches',type:'link',icon:'ev_station',role:'user'},
    {state:'choisirabonnement',name:'Choisir Plan',type:'link',icon:'subscriptions',role:'user'},
    {state:'facture',name:'View Facture',type:'link',icon:'local_printshop',role:''},
    {state:'paiement',name:'View Paiement',type:'link',icon:'paid',role:''},
    {state:'contrat',name:'View Contrat',type:'link',icon:'task',role:''},
    {state:'recharge',name:'Ajout Recharge',type:'link',icon:'library_add',role:'user'},
    {state:'borne',name:'Manage Borne',type:'link',icon:'ev_station',role:'admin'},
    {state:'voiture',name:'Manage Voiture',type:'link',icon:'electric_car',role:'admin'},
    {state:'user',name:'Manage User',type:'link',icon:'people',role:'admin'}
]

@Injectable()
export class MenuItems {
    getMenuitem():Menu[]{
        return MENUITEMS;
    }
}