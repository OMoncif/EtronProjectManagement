import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { CdkTableModule } from '@angular/cdk/table';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MaterialRoutes } from './material.routing';
import { MaterialModule } from '../shared/material-module';
import { ViewBillProductsComponent } from './dialog/view-bill-products/view-bill-products.component';
import { ConfirmationComponent } from './dialog/confirmation/confirmation.component';
import { ChangePasswordComponent } from './dialog/change-password/change-password.component';
import { ManageCategoryComponent } from './manage-category/manage-category.component';
import { CategoryComponent } from './dialog/category/category.component';
import { ManageProductComponent } from './manage-product/manage-product.component';
import { ProductComponent } from './dialog/product/product.component';
import { ManageOrderComponent } from './manage-order/manage-order.component';
import { ViewBillComponent } from './view-bill/view-bill.component';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { UserComponent } from './dialog/user/user.component';
import { AbonnementComponent } from './dialog/abonnement/abonnement.component';
import { BorneComponent } from './dialog/borne/borne.component';
import { VoitureComponent } from './dialog/voiture/voiture.component';
import { RechargeComponent } from './dialog/recharge/recharge.component';
import { ManageAbonnementComponent } from './manage-abonnement/manage-abonnement.component';
import { ManageVoitureComponent } from './manage-voiture/manage-voiture.component';
import { ManageBorneComponent } from './manage-borne/manage-borne.component';
import { ManagePaiementComponent } from './manage-paiement/manage-paiement.component';
import { ManageFactureComponent } from './manage-facture/manage-facture.component';
import { ManageContratComponent } from './manage-contrat/manage-contrat.component';
import { ManageRechargeComponent } from './manage-recharge/manage-recharge.component';
import { ChoisirAbonnementComponent } from './choisir-abonnement/choisir-abonnement.component';
import { DemandeFactureComponent } from './dialog/demande-facture/demande-facture.component';
import { ListerBorneComponent } from './lister-borne/lister-borne.component';
import { DemandeBorneComponent } from './dialog/demande-borne/demande-borne.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(MaterialRoutes),
    MaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    CdkTableModule
  ],
  providers: [],
  declarations: [
    ViewBillProductsComponent,
    ConfirmationComponent,
    ChangePasswordComponent,
    ManageCategoryComponent,
    CategoryComponent,
    ManageProductComponent,
    ProductComponent,
    ManageOrderComponent,
    ViewBillComponent,
    ManageUserComponent,
    UserComponent,
    AbonnementComponent,
    BorneComponent,
    VoitureComponent,
    RechargeComponent,
    ManageAbonnementComponent,
    ManageVoitureComponent,
    ManageBorneComponent,
    ManagePaiementComponent,
    ManageFactureComponent,
    ManageContratComponent,
    ManageRechargeComponent,
    ChoisirAbonnementComponent,
    DemandeFactureComponent,
    ListerBorneComponent,
    DemandeBorneComponent
  ]
})
export class MaterialComponentsModule {}
