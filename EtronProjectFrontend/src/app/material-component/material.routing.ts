import { Routes } from '@angular/router';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ManageCategoryComponent } from './manage-category/manage-category.component';
import { RouteGuardService } from '../services/route-guard.service';
import { ManageProductComponent } from './manage-product/manage-product.component';
import { ManageOrderComponent } from './manage-order/manage-order.component';
import { ViewBillComponent } from './view-bill/view-bill.component';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { ManageAbonnementComponent } from './manage-abonnement/manage-abonnement.component';
import { ManageFactureComponent } from './manage-facture/manage-facture.component';
import { ManagePaiementComponent } from './manage-paiement/manage-paiement.component';
import { ManageContratComponent } from './manage-contrat/manage-contrat.component';
import { ManageBorneComponent } from './manage-borne/manage-borne.component';
import { ManageVoitureComponent } from './manage-voiture/manage-voiture.component';
import { ManageRechargeComponent } from './manage-recharge/manage-recharge.component';
import { ChoisirAbonnementComponent } from './choisir-abonnement/choisir-abonnement.component';
import { ListerBorneComponent } from './lister-borne/lister-borne.component';


export const MaterialRoutes: Routes = [
    {
        path:'category',
        component:ManageCategoryComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },
    {
        path:'product',
        component:ManageProductComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },
    {
        path:'order',
        component:ManageOrderComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin','user']
        }
    },
    {
        path:'bill',
        component:ViewBillComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin','user']
        }
    },
    {
        path:'user',
        component:ManageUserComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },
    
    {
        path:'facture',
        component:ManageFactureComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin','user']
        }
    },
    {
        path:'paiement',
        component:ManagePaiementComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin','user']
        }
    },
    {
        path:'contrat',
        component:ManageContratComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin','user']
        }
    },
    {
        path:'borne',
        component:ManageBorneComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },
    {
        path:'voiture',
        component:ManageVoitureComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },
    {
        path:'abonnement',
        component:ManageAbonnementComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['admin']
        }
    },
    {
        path:'recharge',
        component:ManageRechargeComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['user']
        }
    },
    {
        path:'choisirabonnement',
        component:ChoisirAbonnementComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['user']
        }
    },
    {
        path:'bornesproches',
        component:ListerBorneComponent,
        canActivate:[RouteGuardService],
        data: {
            expectedRole: ['user']
        }
    },
];
