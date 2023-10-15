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
    {state:'facture',name:'View Facture',type:'link',icon:'inventory_2',role:''},
    {state:'paiement',name:'View Paiement',type:'link',icon:'inventory_3',role:''},
    {state:'contrat',name:'View Contrat',type:'link',icon:'inventory_1',role:''},
    {state:'borne',name:'Manage Borne',type:'link',icon:'shopping_cart',role:'admin'},
    {state:'voiture',name:'Manage Voiture',type:'link',icon:'backup_table',role:'admin'},
    {state:'user',name:'Manage User',type:'link',icon:'people',role:'admin'}
]

@Injectable()
export class MenuItems {
    getMenuitem():Menu[]{
        return MENUITEMS;
    }
}