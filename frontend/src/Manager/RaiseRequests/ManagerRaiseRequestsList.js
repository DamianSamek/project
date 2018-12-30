import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';
import { Link } from 'react-router-dom';
import axios from 'axios';

class ManagerRaiseRequestList extends Component {

  emptyItem = {
    accepted: false
  }
  constructor(props) {
    super(props);
    this.state = {raiseRequests: [], isLoading: true, item: this.emptyItem};
    this.handleButtonClick = this.handleButtonClick.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});


    axios.get('/api/raiserequest?projection=withAgreementAndEmployee', {
      headers: {
        Authorization: window.localStorage.getItem("token") 
      }
    })
  .then(
    response => {
        const data = response.data._embedded.raiserequest;
        this.setState({raiseRequests: data, isLoading:false});
            
    }    
  )
  .catch(function (error) {
    // handle error
    console.log(error);
  })
  .then(function () {
    // always executed
  });

  }

  async handleButtonClick(event, id) {

    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
    //  const {item} = this.state;

    await fetch(`/api/raiserequest/${id}`, {
      method: 'PATCH',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("token")
      },
      body: JSON.stringify(item),
    }).then(() => {
      let updatedRaiseRequests = [...this.state.raiseRequests].filter(i => i.id !== id);
      this.setState({raiseRequests: updatedRaiseRequests});
    });
  }
  

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER"){
    const {raiseRequests, isLoading} = this.state;
        console.log(raiseRequests);
    if (isLoading) {
      return <p>Loading...</p>;
    }
    const raiseRequestsList = raiseRequests.map(raiseRequest => {
    //   const address = `${group.address || ''} ${group.city || ''} ${group.stateOrProvince || ''}`;
      return <tr key={raiseRequest.id}>
        <td style={{whiteSpace: 'nowrap'}}>{raiseRequest.number}</td>
        <td>{raiseRequest.firstName} {raiseRequest.secondName}</td>
        <td>{raiseRequest.salaryRequest}</td>

        
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" name="accepted" value={true} onClick={(e) => this.handleButtonClick(e,raiseRequest.id)}>Zatwierdź</Button>
            <Button size="sm" color="danger" name="accepted" value={false} onClick={(e) => {this.handleButtonClick(e,raiseRequest.id)}}>Odrzuć</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <ManagerAppNavbar/>
        <Container fluid>
          <h3>Zapytania o podwyżkę</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Numer umowy</th>
              <th width="20%">Pracownik</th>
              <th>Propozycja</th>
              <th width="10%">Akcja</th>
              
            </tr>
            </thead>
            <tbody>
            {raiseRequestsList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  } else return <div>BRAK DOSTĘPU</div>
  }
}

export default ManagerRaiseRequestList;