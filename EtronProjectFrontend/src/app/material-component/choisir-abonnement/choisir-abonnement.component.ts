import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { AbonnementService } from 'src/app/services/abonnement.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { AbonnementComponent } from '../dialog/abonnement/abonnement.component';
import { EtronService } from 'src/app/services/etron.service';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-choisir-abonnement',
  templateUrl: './choisir-abonnement.component.html',
  styleUrls: ['./choisir-abonnement.component.scss']
})
export class ChoisirAbonnementComponent {
  displayedColumns: string[] = ['type', 'fraismois', 'dureeContrat', 'fraisAC', 'fraisDCHPC', 'fraisHautePuissance', 'fraisBlocageAC', 'fraisBlocageDC'];
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>([]);
  responseMessage: any;
  selectedAbonnementType: string = ''; // Property to store the selected Abonnement type

  isSubscribed: boolean = false;


  planForm:any = FormGroup;
  constructor(
    private ngxService: NgxUiLoaderService,
    private abonnementService: AbonnementService,
    private etronService : EtronService,
    private snackbarService: SnackbarService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ngxService.start();
    this.tableData();
  }

  tableData() {
    this.abonnementService.getPlans().subscribe(
      (response: any) => {
        this.ngxService.stop();
        console.log('API Response:', response);
        this.dataSource.data = response;
      },
      (error: any) => {
        this.ngxService.stop();
        console.log(error);
        if (error.error?.message) {
          this.responseMessage = error.error?.message;
        } else {
          this.responseMessage = GlobalConstants.genericError;
        }
        this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
      }
    );
  }

  // Method to handle the subscription when the user clicks the "S'abonner" button
  onSubscribeClicked(abonnementType: string) {
    if (abonnementType) {
      const requestMap = {
        typeAbonnement: abonnementType
      };
  
      this.etronService.choisirFormuleAbonnement(requestMap).subscribe(
        (response: any) => {
          this.responseMessage = response;
          this.isSubscribed = true;
        },
        (error: any) => {
          console.error('Subscription Error:', error);
          this.responseMessage = error.error?.message;
  
          this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
        }
      );
    } else {
      this.responseMessage = 'Abonnement type not selected';
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    }
  }
  

  handlePayer(abonnementType: string) {
    if (abonnementType) {
      const requestMap = {
        typeAbonnement: abonnementType
      };
  
      this.etronService.payerContrat(requestMap).subscribe(
        (response: any) => {
          console.log('Subscription Response:', response);
          this.isSubscribed = true;
        },
        (error: any) => {
          console.error('Subscription Error:', error);
        }
      );
    } else {
      console.warn('Abonnement type not selected');
    }
  }
  
}
