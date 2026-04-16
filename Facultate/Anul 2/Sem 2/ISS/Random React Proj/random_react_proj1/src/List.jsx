import PropTypes from 'prop-types'

function List(props){

    const itemNames = props.items.map(item => <li>{item.name}</li>)
    const category = props.category;

    return (
        <>
            <h2>{category}</h2>
            <ul>
                {itemNames}
            </ul>
        </>
    )

}
List.propTypes = {
    items: PropTypes.arrayOf(PropTypes.shape({id: PropTypes.number.isRequired, name: PropTypes.string.isRequired, category: PropTypes.string.isRequired})).isRequired,
    category: PropTypes.string.isRequired
}

List.defaultProps = {
    category: 'category_fill',
    items: [{id: 0, name: 'guest', category: 'guest'}]
}
export default List