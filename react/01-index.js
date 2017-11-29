import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';

function Greeting(props) {
    const isLogin = props.isLoggedIn;
    if (isLogin) {
        return <h1>ok</h1>;
    }
    return <h4>welcome,stranger!</h4>
}

function LoginButton(props) {
    return (
        <button onClick={props.onClick}>Login</button>
    );
}
function LogoutButton(props) {
    return (
        <button onClick={props.onClick}>Logout</button>
    );
}
class LoginControl extends React.Component {
    constructor(props) {
        super(props);
        this.handleLogin = this.handleLogin.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.state = {
            isLoggedIn: true
        }
    }

    handleLogout() {
        this.setState({
            isLoggedIn: false
        });
    }
    handleLogin() {
        this.setState({
            isLoggedIn: true
        });
    }
    render() {
        const isLoggedIn = this.state.isLoggedIn;
        let button = null;
        if (isLoggedIn) {
            button = <LogoutButton onClick={this.handleLogout} />
        } else {
            button = <LoginButton onClick={this.handleLogin} />
        }
        return (
            <div>
                <Greeting isLoggedIn={isLoggedIn} />
                {button}
            </div>
        );
    }
}

function Mailbox(params) {
    const unreadMessages = params.messages;
    return (
        <div>
            <h1>Hello </h1>{unreadMessages.length > 0 &&
                <h3>You have {unreadMessages.length} unread messages</h3>}
        </div>
    );
}

function WarningBanner(params) {
    if (!params.warn) {
        return null;
    }
    return (
        <div>warning!</div>
    );
}

class Page extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showWarning: true
        }
        this.handleClick = this.handleClick.bind(this);
    }
    handleClick() {
        this.setState((prevState) => ({
            showWarning: !prevState.showWarning
        }));
    }
    render() {
        return (
            <div>
                <WarningBanner warn={this.state.showWarning} />
                <button onClick={this.handleClick}>{this.state.showWarning ? 'Hide' : 'Show'}</button>
            </div>
        )
    }
}

function NumberList(params) {
    const numbers = params.numbers;
    const liItems = numbers.map((number) => <li key={number.toString()}>{number}</li>);
    return (
        <ul>{liItems}</ul>
    );
}

function ListItem(params) {
    //do not need key
    return <li>{params.value}</li>
}
function NumberList2(params) {
    const numbers = params.numbers;
    //should have key
    const listItems = numbers.map((number) => <ListItem key={number.toString()} value={number} />);
    return <ul>{listItems}</ul>;
}

function Post(post) {
    return (
        <div >
            <h3>{post.title}</h3>
            <p>{post.content}</p>
        </div>
    );
}

function Blog(params) {
    const sidebar = (
        <ul>
            {params.posts.map((post) =>
                <li key="{post.id}">{post.title}</li>
            )}
        </ul>
    );
    const content = params.posts.map((post) =>
        <Post key={post.id} id={post.id} title={post.title} content={post.content} />
    );
    return (
        <div>
            {sidebar}
            <hr />
            {content}
        </div>
    );
}

class NameForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: ''
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({
            value: event.target.value.toUpperCase()
        });
    }

    handleSubmit(event) {
        console.log('submit value is:' + this.state.value);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Name:
                    <input type="text" onChange={this.handleChange} value={this.state.value} />
                </label>
                <input type="submit" value="submit" />
            </form>
        );
    }
}

class EssayForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: 'write some essay'
        }
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({
            value: event.target.value
        });
    }

    handleSubmit(event) {
        console.log('submit essay is:' + this.state.value);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Essay:
                    <textarea onChange={this.handleChange} value={this.state.value} />
                </label>
                <input type="submit" value="submit" />
            </form>
        );
    }
}

class FlavorForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = { value: 'coconut' };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({ value: event.target.value });
    }

    handleSubmit(event) {
        console.log('Your favorite flavor is: ' + this.state.value);
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    Pick your favorite La Croix flavor:
                    <select value={this.state.value} onChange={this.handleChange}>
                        <option value="grapefruit">Grapefruit</option>
                        <option value="lime">Lime</option>
                        <option value="coconut">Coconut</option>
                        <option value="mango">Mango</option>
                    </select>
                </label>
                <input type="submit" value="Submit" />
            </form>
        );
    }
}

class Reservation extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isGoing: true,
            numberOfGuests: 2
        };

        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        console.log(value);

        this.setState({
            [name]: value
        });
    }

    render() {
        return (
            <form>
                <label>
                    Is going:
          <input
                        name="isGoing"
                        type="checkbox"
                        checked={this.state.isGoing}
                        onChange={this.handleInputChange} />
                </label>
                <br />
                <label>
                    Number of guests:
          <input
                        name="numberOfGuests"
                        type="number"
                        value={this.state.numberOfGuests}
                        onChange={this.handleInputChange} />
                </label>
            </form>
        );
    }
}

function BoilingVerdict(params) {
    if (params.celsius >= 100) {
        return <p>The water would boil.</p>
    }
    return <p>The water would not boil.</p>
}

class Caculater extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            temprature: ''
        }
        this.handleChange = this.handleChange.bind(this);
    }
    handleChange(e) {
        this.setState({
            temprature: e.target.value
        });
    }
    render() {
        const temp = this.state.temprature;
        return (
            <fieldset>
                <legend>Input celsius temprature:</legend>
                <input type="text" onChange={this.handleChange} value={temp} />
                <BoilingVerdict celsius={parseFloat(temp)} />
            </fieldset>
        );
    }
}

const scaleNames = {
    c: "Celsius",
    f: "Fahrenheit"
}

class TempratureInput extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            temprature: ''
        }
        this.handleChange = this.handleChange.bind(this);
    }
    handleChange(e) {
        this.setState({
            temprature: e.target.value
        });
    }
    render() {
        const temp = this.state.temprature;
        const scale = this.props.scale;
        return (
            <fieldset>
                <legend>Input {scaleNames[scale]} temprature:</legend>
                <input type="text" onChange={this.handleChange} value={temp} />
                <BoilingVerdict celsius={parseFloat(temp)} />
            </fieldset>
        );
    }
}

class Caculater2 extends React.Component {
    render() {
        return (
            <div>
                <TempratureInput scale='f' />
                <TempratureInput scale='c' />
            </div>
        );
    }
}

function toCelsius(fahrenheit) {
    return (fahrenheit - 32) * 5 / 9;
}

function toFahrenheit(celsius) {
    return (celsius * 9 / 5) + 32;
}

function tryConvert(temp, convert) {
    const input = parseFloat(temp);
    if (Number.isNaN(input)) {
        return '';
    }
    const output = convert(input);
    const rounded = Math.round(output * 1000) / 1000;
    return rounded.toString();
}

class TempratureInput2 extends React.Component {
    constructor(props) {
        super(props);
        // this.state = {
        //     temprature: ''
        // }
        this.handleChange = this.handleChange.bind(this);
    }
    handleChange(e) {
        // this.setState({
        //     temprature: e.target.value
        // });
        this.props.onTempratureChange(e.target.value);
    }
    render() {
        // const temp = this.state.temprature;
        const temp = this.props.temprature;
        const scale = this.props.scale;
        return (
            <fieldset>
                <legend>Input {scaleNames[scale]} temprature:</legend>
                <input type="text" onChange={this.handleChange} value={temp} />
                {/* <BoilingVerdict celsius={parseFloat(temp)} /> */}
            </fieldset>
        );
    }
}

class Caculater3 extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            temprature: '', scale: 'c'
        }
        this.handleCelciusChange = this.handleCelciusChange.bind(this);
        this.handleFehrenheitChange = this.handleFehrenheitChange.bind(this);
    }

    handleCelciusChange(temp) {
        this.setState({
            scale: 'c', temprature: temp
        });
    }
    handleFehrenheitChange(temp) {
        console.log(temp);
        this.setState({
            scale: 'f', temprature: temp
        });
    }
    render() {
        const scale = this.state.scale;
        const temp = this.state.temprature;
        const celsius = scale === 'f' ? tryConvert(temp, toCelsius) : temp;
        const fahrenheit = scale === 'c' ? tryConvert(temp, toFahrenheit) : temp;
        return (
            <div>
                <TempratureInput2 scale="f" temprature={fahrenheit}
                    onTempratureChange={this.handleFehrenheitChange} />
                <TempratureInput2 scale="c" temprature={celsius}
                    onTempratureChange={this.handleCelciusChange} />
                <BoilingVerdict celsius={parseFloat(celsius)} />
            </div>
        );
    }
}

function FancyBorder(params) {
    return (
        <div className={'FancyBorder FancyBorder-' + params.color}>
            {params.children}
        </div>
    );
}

function WelcomeDialog(params) {
    return (
        <FancyBorder color="blue">
            <h1 className="Dialog-title">Welcome</h1>
            <p className="Dialog-message">Welcome !</p>
        </FancyBorder>
    );
}

function Contacts() {
    return <div className="Contacts" />;
}

function Chat() {
    return <div className="Chat" />;
}

function SplitPanel(props) {
    return (
        <div className="SplitPane">
            <div className="SplitPane-left">{props.left}</div>
            <div className="SplitPane-right">{props.right}</div>
        </div>
    );
}

function Dialog(props) {
    return (
        <FancyBorder color="blue">
            <h1 className="Dialog-title">{props.title}</h1>
            <p className="Dialog-message">{props.message}</p>
            {props.children}
        </FancyBorder>
    );
}

function WelcomeDialog2(props) {
    return <Dialog title="Hello" message="message" />
}

class SignUpDialog extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.handleSignUp = this.handleSignUp.bind(this);
        this.state = { login: '' };
    }

    render() {
        return (
            <Dialog title="Mars Exploration Program"
                message="How should we refer to you?">
                <input value={this.state.login}
                    onChange={this.handleChange} />

                <button onClick={this.handleSignUp}>
                    Sign Me Up!
        </button>
            </Dialog>
        );
    }

    handleChange(e) {
        this.setState({ login: e.target.value });
    }

    handleSignUp() {
        alert(`Welcome aboard, ${this.state.login}!`);
    }
}

////////////////////////////

class ProductCategoryRow extends React.Component {
    render() {
        const category = this.props.category;
        return (
            <tr>
                <th colSpan="2">
                    {category}
                </th>
            </tr>
        );
    }
}

class ProductRow extends React.Component {
    render() {
        const product = this.props.product;
        const name = product.stocked ?
            product.name : <span style={{ color: 'red' }}>    {product.name}</span>;

        return (
            <tr>
                <td>{name}</td>
                <td>{product.price}</td>
            </tr>
        );
    }
}

class ProductTable extends React.Component {
    render() {
        const rows = [];
        let lastCategory = null;

        this.props.products.forEach((product) => {
            if (product.category !== lastCategory) {
                rows.push(
                    <ProductCategoryRow
                        category={product.category}
                        key={product.category} />
                );
            }
            rows.push(
                <ProductRow
                    product={product}
                    key={product.name} />
            );
            lastCategory = product.category;
        });

        return (
            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>{rows}</tbody>
            </table>
        );
    }
}

class SearchBar extends React.Component {
    render() {
        return (
            <form>
                <input type="text" placeholder="Search..." />
                <p><input type="checkbox" />{' '}Only show products in stock</p>
            </form>
        );
    }
}

class FilterableProductTable extends React.Component {
    render() {
        return (
            <div>
                <SearchBar />
                <ProductTable products={this.props.products} />
            </div>
        );
    }
}


const PRODUCTS = [
    { category: 'Sporting Goods', price: '$49.99', stocked: true, name: 'Football' },
    { category: 'Sporting Goods', price: '$9.99', stocked: true, name: 'Baseball' },
    { category: 'Electronics', price: '$99.99', stocked: true, name: 'iPod Touch' },
    { category: 'Sporting Goods', price: '$29.99', stocked: false, name: 'Basketball' },
    { category: 'Electronics', price: '$399.99', stocked: false, name: 'iPhone 5' },
    { category: 'Electronics', price: '$199.99', stocked: true, name: 'Nexus 7' }
];

////////////////////////////

class App extends React.Component {
    render() {
        const messages = ['React', 'Re: React', 'Re:Re: React'];
        const numbers = [1, 2, 3, 4, 5];
        const posts = [
            { id: 1, title: 'Hello World', content: 'Welcome to learning React!' },
            { id: 2, title: 'Installation', content: 'You can install React from npm.' }
        ];
        return (
            <div>
                <FilterableProductTable products={PRODUCTS} />
                <h1>Hello Test!</h1>
                <SignUpDialog />
                <WelcomeDialog2 />
                <SplitPanel left={<Contacts />} right={<Chat />} />
                <WelcomeDialog />
                <Caculater />
                <Caculater2 />
                <Caculater3 />
                <NameForm />
                <EssayForm />
                <FlavorForm />
                <Reservation />
                <LoginControl />
                <Mailbox messages={messages} />
                <Page />
                <NumberList numbers={numbers} />
                <NumberList2 numbers={numbers} />
                {/* <Blog posts={posts} /> */}
            </div>
        );
    }
}
ReactDOM.render(<App />, document.getElementById('root'));