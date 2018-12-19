import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import ManagerAppNavbar from '../ManagerAppNavbar';

class ManagerUserEdit extends Component {

  emptyItem = {
    firstName: '',
    secondName: '',
    email: '',
    enabled: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const user = await (await fetch(`/api/user/${this.props.match.params.id}`,{
        headers: {
          'Authorization': localStorage.getItem("token")
        }
      })).json();
      this.setState({item: user});
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/api/user', {
      method: (item.id) ? 'PATCH' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/manager/user');
  }

  render() {
    if(localStorage.getItem("loggedUserRole")==="ROLE_MANAGER"){
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edycja użytkownika' : 'Dodawanie użytkownika'}</h2>;

    return <div>
      <ManagerAppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="firstName">Imię</Label>
            <Input type="text" name="firstName" id="firstName" value={item.firstName || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Label for="secondName">Nazwisko</Label>
            <Input type="text" name="secondName" id="secondName" value={item.secondName || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
          </FormGroup>
          <FormGroup>
            <Label for="email">E-mail</Label>
            <Input type="text" name="email" id="email" value={item.email || ''}
                   onChange={this.handleChange} autoComplete="address-level1"/>
                   </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Zapisz</Button>{' '}
            <Button color="secondary" tag={Link} to="/manager/user">Anuluj</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>}else return <div>BRAK DOSTĘPU</div>
  }
}

export default withRouter(ManagerUserEdit);